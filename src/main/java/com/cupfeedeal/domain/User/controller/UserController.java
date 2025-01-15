package com.cupfeedeal.domain.User.controller;

import com.cupfeedeal.domain.User.dto.response.UserInfoResponseDto;
import com.cupfeedeal.domain.User.service.UserService;
import com.cupfeedeal.global.common.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse<UserInfoResponseDto>> getUserInfo(@PathVariable Long userId) {
        CommonResponse<UserInfoResponseDto> userInfoResponseDto = userService.getUserInfo(userId);
        return ResponseEntity.ok().body(userInfoResponseDto);
    }

}
