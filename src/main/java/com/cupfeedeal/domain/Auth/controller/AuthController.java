package com.cupfeedeal.domain.Auth.controller;

import com.cupfeedeal.domain.Auth.service.AuthService;
import com.cupfeedeal.domain.User.dto.response.UserResponseDto;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.global.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signin")
    public CommonResponse<UserResponseDto.JoinResultDto> signIn(@RequestParam("code") String accessCode, HttpServletResponse httpServletResponse) {
        User user = authService.oOAuthSignIn(accessCode, httpServletResponse);
        return CommonResponse.onSuccess(UserConverter.toJoinResultDto(user));
    }

}
