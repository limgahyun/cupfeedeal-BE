package com.cupfeedeal.domain.User.service;

import com.cupfeedeal.domain.Cupcat.entity.Cupcat;
import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.Cupcat.repository.UserCupcatRepository;
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

        String cupcatImageUrl = getCupcatImgUrl(user);

        UserInfoResponseDto userInfoResponseDto = new UserInfoResponseDto(user.getUsername(), user.getUser_level(), cupcatImageUrl);

        return userInfoResponseDto;
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
