package com.cupfeedeal.domain.Auth.service;

import com.cupfeedeal.domain.Auth.dto.responseDto.KakaoUserInfoResponseDto;
import com.cupfeedeal.domain.Auth.dto.responseDto.LoginResponseDto;
import com.cupfeedeal.domain.Auth.security.JwtTokenProvider;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
import com.cupfeedeal.domain.UserSubscription.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserSubscriptionRepository userSubscriptionRepository;

    public LoginResponseDto login(KakaoUserInfoResponseDto userInfoResponseDto) {
        if (userRepository.findByEmail(userInfoResponseDto.getKakaoAccount().email).isPresent()) {
            User user = userRepository.findByEmail(userInfoResponseDto.getKakaoAccount().email).get();
            Integer subscription_count = userSubscriptionRepository.findAllByUser(user).size();

            String token = jwtTokenProvider.createToken(user.getUserId());

            // 디버깅: 토큰이 생성되지 않았다면 로그 출력
            if (token == null || token.isEmpty()) {
                throw new IllegalStateException("JWT Token generation failed");
            }

            LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                    .username(user.getUsername())
                    .token(token)
                    .subscription_count(subscription_count)
                    .is_first(false)
                    .build();
            return loginResponseDto;
        }
        else {
            User user = User.builder()
                    .username(userInfoResponseDto.kakaoAccount.profile.nickname)
                    .email(userInfoResponseDto.kakaoAccount.email)
                    .user_level(0)
                    .build();

            userRepository.save(user);

            String token = jwtTokenProvider.createToken(user.getUserId());

            // 디버깅: 토큰이 생성되지 않았다면 로그 출력
            if (token == null || token.isEmpty()) {
                throw new IllegalStateException("JWT Token generation failed");
            }

            LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                    .username(user.getUsername())
                    .token(token)
                    .is_first(true)
                    .subscription_count(userSubscriptionRepository.findAllByUser(user).size())
                    .build();
            log.info("[AuthService] userId: {}", user.getUserId());
            return loginResponseDto;
        }
    }

}
