package com.cupfeedeal.domain.User.service;

import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.Cupcat.repository.UserCupcatRepository;
import com.cupfeedeal.domain.User.dto.response.*;
import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
import com.cupfeedeal.domain.UserSubscription.enumerate.SubscriptionStatus;
import com.cupfeedeal.domain.UserSubscription.repository.UserSubscriptionRepository;
import com.cupfeedeal.global.exception.ApplicationException;
import com.cupfeedeal.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserCupcatRepository userCupcatRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    public UserInfoResponseDto getUserInfo(CustomUserdetails customUserdetails) {
        if (customUserdetails == null || customUserdetails.getUser() == null) {
            throw new ApplicationException(ExceptionCode.USER_NOT_FOUND);
        }

        User user = customUserdetails.getUser();

        // userCupcat 조회
        UserCupcat userCupcat = userCupcatRepository.findTop1ByUserOrderByCreatedAtDesc(user)
                .orElse(null);

        return UserInfoResponseDto.from(user, userCupcat);
    }

    public UserInfoUpdateResponseDto updateUserInfo(CustomUserdetails customUserdetails, String newUsername) {
        User user = customUserDetailService.loadUserByCustomUserDetails(customUserdetails);
        user.setUsername(newUsername);
        userRepository.save(user);

        UserInfoUpdateResponseDto userInfoUpdateResponseDto = new UserInfoUpdateResponseDto(newUsername);

        return userInfoUpdateResponseDto;
    }

    public UserMainInfoResponseDto getUserMainInfo(CustomUserdetails customUserdetails) {
        if (customUserdetails == null || customUserdetails.getUser() == null) {
            return null;
        }
        User user = customUserdetails.getUser();
        Long cupcatId = getCupcatId(user);
        Integer subscription_count = userSubscriptionRepository.countByUserAndSubscriptionStatusIsValid(user);

        return UserMainInfoResponseDto.from(user, subscription_count, cupcatId);
    }

    public Long getCupcatId(User user) {
        Optional<UserCupcat> userCupcat = userCupcatRepository.findTop1ByUserOrderByCreatedAtDesc(user);
        Long cupcatId = userCupcat.isPresent() ? userCupcat.get().getCupcat().getCupcatId() : null;

        return cupcatId;
    }

    public List<CupcatInfoResponseDto> getAllCupcats(User user) {
        List<UserCupcat> userCupcats = userCupcatRepository.findAllByUserOrderByCreatedAtAsc(user);

        return userCupcats.stream()
                .map(CupcatInfoResponseDto::from)
                .toList();
    }

    /*
    지나간 컵캣 정보 조회
     */
    public UserCupcatInfoResponseDto getCupcatInfo(CustomUserdetails customUserdetails) {
        User user = customUserDetailService.loadUserByCustomUserDetails(customUserdetails);

        // user의 모든 컵캣
        List<CupcatInfoResponseDto> cupcats = getAllCupcats(user);

        return UserCupcatInfoResponseDto.from(user, cupcats);
    }
}
