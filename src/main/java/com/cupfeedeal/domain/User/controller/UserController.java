package com.cupfeedeal.domain.User.controller;

import com.cupfeedeal.domain.User.dto.request.UserInfoUpdateRequestDto;
import com.cupfeedeal.domain.User.dto.response.UserInfoResponseDto;
import com.cupfeedeal.domain.User.service.UserService;
import com.cupfeedeal.global.common.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{userId}")
    public ResponseEntity<CommonResponse<String>> updateUserInfo(@PathVariable Long userId, @RequestBody UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
        CommonResponse<String> response = userService.updateUserInfo(userId, userInfoUpdateRequestDto.getUsername());
        return ResponseEntity.ok(response);
    }

}
