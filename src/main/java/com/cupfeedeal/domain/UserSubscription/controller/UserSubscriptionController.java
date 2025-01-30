package com.cupfeedeal.domain.UserSubscription.controller;

import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.UserSubscription.dto.request.UserSubscriptionCreateRequestDto;
import com.cupfeedeal.domain.UserSubscription.dto.response.*;
import com.cupfeedeal.domain.UserSubscription.sevice.UserSubscriptionService;
import com.cupfeedeal.global.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User Subscription", description = "user subscription api")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/userSubscription")
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    @Operation(summary = "user subscription 생성")
    @PostMapping
    public CommonResponse<Void> createUserSubscription(
            @AuthenticationPrincipal CustomUserdetails customUserdetails,
            @RequestBody UserSubscriptionCreateRequestDto userSubscription
    ) {
        userSubscriptionService.createUserSubscription(customUserdetails, userSubscription);
        return new CommonResponse<>("new userSubscription 생성에 성공하였습니다.");
    }

    @Operation(summary = "구독중인 user subscription list 조회")
    @GetMapping
    public CommonResponse<UserSubscriptionValidListResponseDto> getUserSubscriptions(
            @AuthenticationPrincipal CustomUserdetails customUserdetails
    ){
        UserSubscriptionValidListResponseDto userSubscriptions = userSubscriptionService.getUserSubscriptions(customUserdetails);
        return new CommonResponse<>(userSubscriptions, "user subscription list 조회에 성공하였습니다");
    }

    @Operation(summary = "user subscription 사용")
    @PatchMapping("/{userSubscriptionId}")
    public CommonResponse<UserSubscriptionUseResponseDto> useUserSubscription(
            @PathVariable Long userSubscriptionId
    ) {
        UserSubscriptionUseResponseDto responseDto = userSubscriptionService.useSubscription(userSubscriptionId);
        return new CommonResponse<>(responseDto, "user subscription 사용에 성공하였습니다.");
    }

    @Operation(summary = "모든 user subscription list 조회 (만료된 구독권 포함)")
    @GetMapping("/manage")
    public CommonResponse<List<UserSubscriptionManageListResponseDto>> getAllUserSubscriptions(
            @AuthenticationPrincipal CustomUserdetails customUserdetails
    ){
        List<UserSubscriptionManageListResponseDto> allUserSubscriptions = userSubscriptionService.getAllUserSubscriptions(customUserdetails);
        return new CommonResponse<>(allUserSubscriptions, "모든 user subscription list 조회에 성공하였습니다");
    }

    @Operation(summary = "user subscription 취소 (환불)")
    @PatchMapping("/cancel/{userSubscriptionId}")
    public CommonResponse<UserSubscriptionCancelResponseDto> cancelUserSubscription(
            @PathVariable Long userSubscriptionId
    ){
        UserSubscriptionCancelResponseDto responseDto = userSubscriptionService.cancelSubscription(userSubscriptionId);
        return new CommonResponse<>(responseDto, "구독권 취소에 성공하였습니다.");
    }
}
