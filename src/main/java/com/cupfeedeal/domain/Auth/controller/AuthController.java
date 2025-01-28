package com.cupfeedeal.domain.Auth.controller;

import com.cupfeedeal.domain.Auth.dto.responseDto.KakaoUserInfoResponseDto;
import com.cupfeedeal.domain.Auth.dto.responseDto.LoginResponseDto;
import com.cupfeedeal.domain.Auth.service.AuthService;
import com.cupfeedeal.domain.Auth.service.KakaoService;
import com.cupfeedeal.global.common.response.CommonResponse;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final KakaoService kakaoService;
    private final AuthService authService;

    @GetMapping("/callback")
    public CommonResponse<?> callback(@RequestParam("code") String code, @RequestParam("redirect_uri") String redirectUri) throws IOException {

        log.info("Received redirect_uri: {}", redirectUri);

        String accessToken = kakaoService.getAccessTokenFromKakao(code, redirectUri);

        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        LoginResponseDto loginResponseDto = authService.login(userInfo);

        return new CommonResponse<>(loginResponseDto, "카카오 로그인에 성공했습니다.");
    }

    @GetMapping("/callback/backend")
    public CommonResponse<?> callbackBackend(@RequestParam("code") String code) throws IOException {

        String redirectUri = "https://api.cupfeedeal.xyz/api/v1/auth/callback";

        String accessToken = kakaoService.getAccessTokenFromKakao(code, redirectUri);

        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        LoginResponseDto loginResponseDto = authService.login(userInfo);

        return new CommonResponse<>(loginResponseDto, "카카오 로그인에 성공했습니다.");
    }


}
