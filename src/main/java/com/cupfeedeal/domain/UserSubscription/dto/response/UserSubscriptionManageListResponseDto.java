package com.cupfeedeal.domain.UserSubscription.dto.response;

import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.UserSubscription.enumerate.SubscriptionStatus;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;

import java.time.LocalDateTime;

public record UserSubscriptionManageListResponseDto (
        Long user_subscription_id,
        Long cafe_id,
        String cafe_name,
        String menu,
        String cafe_subscription_name,
        Integer period,
        Integer price,
        LocalDateTime start,
        LocalDateTime end,
        SubscriptionStatus status
){
    public static UserSubscriptionManageListResponseDto from(UserSubscription userSubscription, Cafe cafe, CafeSubscriptionType cafeSubscriptionType) {
        return new UserSubscriptionManageListResponseDto(
                userSubscription.getUserSubscriptionId(),
                cafe.getId(),
                cafe.getName(),
                "아이스 아메리카노",
                cafeSubscriptionType.getName(),
                cafeSubscriptionType.getPeriod() / 7,
                cafeSubscriptionType.getPrice(),
                userSubscription.getSubscriptionStart(),
                userSubscription.getSubscriptionDeadline(),
                userSubscription.getSubscriptionStatus()
        );
    }
}
