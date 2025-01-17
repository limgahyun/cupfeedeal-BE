package com.cupfeedeal.domain.User.controller;

import com.cupfeedeal.domain.User.dto.request.UserInfoUpdateRequestDto;
import com.cupfeedeal.domain.User.dto.response.PaymentHistoryResponseDto;
import com.cupfeedeal.domain.User.dto.response.UserInfoResponseDto;
import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.User.service.UserService;
import com.cupfeedeal.domain.UserSubscription.sevice.UserSubscriptionService;
import com.cupfeedeal.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    private final UserSubscriptionService userSubscriptionService;

    @GetMapping("")
    public ResponseEntity<CommonResponse<UserInfoResponseDto>> getUserInfo(@AuthenticationPrincipal CustomUserdetails customUserdetails) {
        CommonResponse<UserInfoResponseDto> userInfoResponseDto = userService.getUserInfo(customUserdetails);
        return ResponseEntity.ok().body(userInfoResponseDto);
    }

    @PutMapping("")
    public ResponseEntity<CommonResponse<String>> updateUserInfo(@AuthenticationPrincipal CustomUserdetails customUserdetails, @RequestBody UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
        CommonResponse<String> response = userService.updateUserInfo(customUserdetails, userInfoUpdateRequestDto.getUsername());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}/payment")
    public ResponseEntity<CommonResponse<List<PaymentHistoryResponseDto>>> getUserPaymentHistory(@PathVariable Long userId) {
        List<PaymentHistoryResponseDto> paymentHistory = userSubscriptionService.getUserPaymentHistory(userId);
        return ResponseEntity.ok(new CommonResponse<>(paymentHistory, "유저 결제 내역을 성공적으로 가져왔습니다."));
    }

}
