package com.cupfeedeal.domain.Auth.controller;

import com.cupfeedeal.domain.Auth.dto.responseDto.KakaoUserInfoResponseDto;
import com.cupfeedeal.domain.Auth.dto.responseDto.LoginResponseDto;
import com.cupfeedeal.domain.Auth.security.JwtTokenProvider;
import com.cupfeedeal.domain.Auth.service.AuthService;
import com.cupfeedeal.domain.Auth.service.KakaoService;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
import com.cupfeedeal.global.common.response.CommonResponse;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final KakaoService kakaoService;
    private final AuthService authService;

    @GetMapping("/callback")
    public CommonResponse<?> callback(@RequestParam("code") String code) throws IOException {
        String accessToken = kakaoService.getAccessTokenFromKakao(code);

        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        LoginResponseDto loginResponseDto = authService.login(userInfo);

        return new CommonResponse<>(loginResponseDto, "카카오 로그인에 성공했습니다.");
    }
}
