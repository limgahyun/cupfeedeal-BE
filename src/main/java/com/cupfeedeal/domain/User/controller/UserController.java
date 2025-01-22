package com.cupfeedeal.domain.User.controller;

import com.cupfeedeal.domain.Auth.security.JwtTokenProvider;
import com.cupfeedeal.domain.User.dto.request.UserInfoUpdateRequestDto;
import com.cupfeedeal.domain.User.dto.response.PaymentHistoryResponseDto;
import com.cupfeedeal.domain.User.dto.response.UserInfoResponseDto;
import com.cupfeedeal.domain.User.dto.response.UserInfoUpdateResponseDto;
import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.User.service.UserService;
import com.cupfeedeal.domain.UserSubscription.sevice.UserSubscriptionService;
import com.cupfeedeal.global.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("")
    public CommonResponse<UserInfoResponseDto> getUserInfo(@AuthenticationPrincipal CustomUserdetails customUserdetails) {
//        if (customUserdetails == null) {
//            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
//        }
        UserInfoResponseDto userInfoResponseDto = userService.getUserInfo(customUserdetails);
        return new CommonResponse<> (userInfoResponseDto, "회원 정보를 성공적으로 조회했습니다.");
    }

    @PatchMapping("")
    public CommonResponse<UserInfoUpdateResponseDto> updateUserInfo(@AuthenticationPrincipal CustomUserdetails customUserdetails, @RequestBody UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
        UserInfoUpdateResponseDto userInfoUpdateResponseDto = userService.updateUserInfo(customUserdetails, userInfoUpdateRequestDto.getUsername());

        return new CommonResponse<>(userInfoUpdateResponseDto, "회원 정보를 성공적으로 수정했습니다.");
    }

    @GetMapping("/payment")
    public CommonResponse<List<PaymentHistoryResponseDto>> getUserPaymentHistory(@AuthenticationPrincipal CustomUserdetails customUserdetails) {
        List<PaymentHistoryResponseDto> paymentHistoryResponseDto = userSubscriptionService.getUserPaymentHistory(customUserdetails);
        return new CommonResponse<>(paymentHistoryResponseDto, "유저 결제 내역을 성공적으로 가져왔습니다.");
    }

}
