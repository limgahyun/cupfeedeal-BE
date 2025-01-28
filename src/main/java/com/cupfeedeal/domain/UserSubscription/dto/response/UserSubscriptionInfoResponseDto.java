package com.cupfeedeal.domain.UserSubscription.dto.response;

import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;

import java.time.LocalDateTime;

public record UserSubscriptionInfoResponseDto (
        Long user_subscription_id,
        String menu,
        Integer period,
        Integer price,
        LocalDateTime end
){
    public static UserSubscriptionInfoResponseDto from(UserSubscription userSubscription, CafeSubscriptionType cafeSubscriptionType) {
        return new UserSubscriptionInfoResponseDto(
                userSubscription.getUserSubscriptionId(),
                "아이스 아메리카노",
                cafeSubscriptionType.getPeriod() / 7,
                cafeSubscriptionType.getPrice(),
                userSubscription.getExtendedSubscriptionDeadline()
        );
    }
}

