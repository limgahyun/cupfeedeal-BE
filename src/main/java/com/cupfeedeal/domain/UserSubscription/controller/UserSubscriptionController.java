package com.cupfeedeal.domain.UserSubscription.controller;

import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.UserSubscription.dto.request.UserSubscriptionCreateRequestDto;
import com.cupfeedeal.domain.UserSubscription.sevice.UserSubscriptionService;
import com.cupfeedeal.global.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Subscription", description = "user subscription api")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/userSubscription")
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    @Operation(summary = "user subscription 생성")
    @PostMapping
    public CommonResponse<Void> createUserSubscription(
            @AuthenticationPrincipal CustomUserdetails customUserdetails,
            UserSubscriptionCreateRequestDto userSubscription
    ) {
        userSubscriptionService.createUserSubscription(customUserdetails, userSubscription);
        return new CommonResponse<>("new userSubscription 생성에 성공하였습니다.");
    }
}
