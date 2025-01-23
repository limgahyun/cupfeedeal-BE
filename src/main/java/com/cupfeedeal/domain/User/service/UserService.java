package com.cupfeedeal.domain.User.service;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.Cupcat.repository.UserCupcatRepository;
import com.cupfeedeal.domain.User.dto.response.UserCupcatInfoResponseDto;
import com.cupfeedeal.domain.User.dto.response.UserInfoResponseDto;
import com.cupfeedeal.domain.User.dto.response.UserInfoUpdateResponseDto;
import com.cupfeedeal.domain.User.dto.response.UserMainInfoResponseDto;
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

import java.time.LocalDate;
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


    // 리팩토링 완전 필요
    public UserInfoResponseDto getUserInfo(CustomUserdetails customUserdetails) {
        if (customUserdetails == null || customUserdetails.getUser() == null) {
            throw new ApplicationException(ExceptionCode.USER_NOT_FOUND);
        }

        User user = customUserdetails.getUser();

        String cupcatImageUrl = getCupcatImgUrl(user);

        // 유효한 데이터 넘기도록 수정 필요
        String cafeName = "카페";

        Optional<UserCupcat> userCupcat = userCupcatRepository.findTop1ByUserOrderByCreatedAtAsc(user);
        LocalDate birthDate = (userCupcat.isPresent()) ? userCupcat.get().getCreatedAt().toLocalDate() : null;

        UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto(user.getUsername(), user.getUser_level(), cupcatImageUrl, cafeName, birthDate);

        return userInfoResponseDto;
    }

    public UserCupcatInfoResponseDto getUserCupcatInfo(CustomUserdetails customUserdetails) {
        User user = customUserDetailService.loadUserByCustomUserDetails(customUserdetails);

        UserCupcat userCupcat = userCupcatRepository.findTop1ByUserOrderByCreatedAtAsc(user)
                .orElse(null);

        // 수정 필요
        String cafeName = "카페";

        return UserCupcatInfoResponseDto.from(userCupcat, cafeName);
    }

    public UserInfoUpdateResponseDto updateUserInfo(CustomUserdetails customUserdetails, String newUsername) {
        User user = customUserdetails.getUser();
        user.setUsername(newUsername);
        userRepository.save(user);

        UserInfoUpdateResponseDto userInfoUpdateResponseDto = new UserInfoUpdateResponseDto(newUsername);

        return userInfoUpdateResponseDto;
    }

    public void createUserCupcat(User user, Cupcat cupcat) {
        UserCupcat.builder()
                .user(user)
                .cupcat(cupcat)
                .build();
    }

    public UserMainInfoResponseDto getUserMainInfo(CustomUserdetails customUserdetails) {
        if (customUserdetails == null || customUserdetails.getUser() == null) {
            return null;
        }
        User user = customUserDetailService.loadUserByCustomUserDetails(customUserdetails);
        String cupcatImageUrl = getCupcatImgUrl(user);
        Integer subscription_count = userSubscriptionRepository.countAllByUser(user, SubscriptionStatus.VALID);

        return UserMainInfoResponseDto.from(user, subscription_count, cupcatImageUrl);
    }

    public String getCupcatImgUrl(User user) {
        Optional<UserCupcat> userCupcat = userCupcatRepository.findTop1ByUserOrderByCreatedAtAsc(user);
        String cupcatImageUrl = userCupcat.isPresent() ? userCupcat.get().getCupcat().getImageUrl() : null;

        return cupcatImageUrl;
    }
}
