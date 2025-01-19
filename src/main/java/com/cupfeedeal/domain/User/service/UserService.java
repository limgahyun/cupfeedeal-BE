package com.cupfeedeal.domain.User.service;

import com.cupfeedeal.domain.Auth.security.JwtTokenProvider;
import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.Cupcat.repository.UserCupcatRepository;
import com.cupfeedeal.domain.User.dto.response.UserInfoResponseDto;
import com.cupfeedeal.domain.User.dto.response.UserInfoUpdateResponseDto;
import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
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

    public UserInfoResponseDto getUserInfo(CustomUserdetails customUserdetails) {
        if (customUserdetails == null || customUserdetails.getUser() == null) {
            throw new ApplicationException(ExceptionCode.USER_NOT_FOUND);
        }

        User user = customUserdetails.getUser();

        Optional<UserCupcat> userCupcat = userCupcatRepository.findByUser(user);
        String cupcatImageUrl = userCupcat.isPresent() ? userCupcat.get().getCupcat().getImageUrl() : null;

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
}
