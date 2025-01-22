package com.cupfeedeal.domain.UserSubscription.dto.response;

import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;

import java.time.LocalDateTime;

public record UserSubscriptionInfoResponseDto (
        Long user_subscription_id,
        Integer period,
        LocalDateTime end
){
    public static UserSubscriptionInfoResponseDto from(UserSubscription userSubscription, CafeSubscriptionType cafeSubscriptionType) {
        return new UserSubscriptionInfoResponseDto(
                userSubscription.getUserSubscriptionId(),
                cafeSubscriptionType.getPeriod() / 7,
                userSubscription.getSubscriptionDeadline()
        );
    }
}

