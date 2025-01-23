package com.cupfeedeal.domain.User.controller;

import com.cupfeedeal.domain.User.dto.request.UserInfoUpdateRequestDto;
import com.cupfeedeal.domain.User.dto.response.*;
import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.User.service.UserService;
import com.cupfeedeal.domain.UserSubscription.sevice.UserSubscriptionService;
import com.cupfeedeal.global.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "user api")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    private final UserSubscriptionService userSubscriptionService;

    @Operation(summary = "my page user 정보 조회")
    @GetMapping("")
    public CommonResponse<UserInfoResponseDto> getUserInfo(@AuthenticationPrincipal CustomUserdetails customUserdetails) {
//        if (customUserdetails == null) {
//            throw new IllegalArgumentException("인증되지 않은 사용자입니다.");
//        }
        UserInfoResponseDto userInfoResponseDto = userService.getUserInfo(customUserdetails);
        return new CommonResponse<> (userInfoResponseDto, "회원 정보를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "메인 홈에서 필요한 유저 정보 조회")
    @GetMapping("/home")
    public CommonResponse<UserInfoHomeResponseDto> getUserInfoHome(@AuthenticationPrincipal CustomUserdetails customUserdetails) {
        UserInfoHomeResponseDto userInfoHomeResponseDto = userService.getUserInfoHome(customUserdetails);
        return new CommonResponse<> (userInfoHomeResponseDto, "회원 정보를 성공적으로 조회했습니다.");
    }

    @Operation(summary = "user nickname 변경")
    @PatchMapping("")
    public CommonResponse<UserInfoUpdateResponseDto> updateUserInfo(@AuthenticationPrincipal CustomUserdetails customUserdetails, @RequestBody UserInfoUpdateRequestDto userInfoUpdateRequestDto) {
        UserInfoUpdateResponseDto userInfoUpdateResponseDto = userService.updateUserInfo(customUserdetails, userInfoUpdateRequestDto.getUsername());

        return new CommonResponse<>(userInfoUpdateResponseDto, "회원 정보를 성공적으로 수정했습니다.");
    }

    @Operation(summary = "user 결제 내역 조회")
    @GetMapping("/payment")
    public CommonResponse<List<PaymentHistoryResponseDto>> getUserPaymentHistory(@AuthenticationPrincipal CustomUserdetails customUserdetails) {
        List<PaymentHistoryResponseDto> paymentHistoryResponseDto = userSubscriptionService.getUserPaymentHistory(customUserdetails);
        return new CommonResponse<>(paymentHistoryResponseDto, "유저 결제 내역을 성공적으로 가져왔습니다.");
    }

    @Operation(summary = "main page user 정보 조회")
    @GetMapping("/main")
    public CommonResponse<UserMainInfoResponseDto> getUserMainInfo(@AuthenticationPrincipal CustomUserdetails customUserdetails) {
        UserMainInfoResponseDto userMainInfo = userService.getUserMainInfo(customUserdetails);
        return new CommonResponse<> (userMainInfo, "회원 정보를 성공적으로 조회했습니다.");
    }
}
