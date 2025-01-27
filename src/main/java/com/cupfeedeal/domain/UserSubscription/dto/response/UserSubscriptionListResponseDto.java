package com.cupfeedeal.domain.UserSubscription.dto.response;

import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;

import java.time.LocalDateTime;

public record UserSubscriptionListResponseDto (
        Long user_subscription_id,
        Long cafe_id,
        String cafe_name,
        String menu,
        Integer period,
        Integer price,
        Double saved_cups,
        Boolean is_used,
        Integer visit,
        LocalDateTime start,
        LocalDateTime end,
        Integer remaining_days
){
    public static UserSubscriptionListResponseDto from(UserSubscription userSubscription, Cafe cafe, CafeSubscriptionType cafeSubscriptionType, Double saved_cups, Integer remaining_days) {
        return new UserSubscriptionListResponseDto(
                userSubscription.getUserSubscriptionId(),
                cafe.getId(),
                cafe.getName(),
                "아이스 아메리카노",
                cafeSubscriptionType.getPeriod() / 7,
                cafeSubscriptionType.getPrice(),
                saved_cups,
                userSubscription.getIsUsed(),
                userSubscription.getUsingCount(),
                userSubscription.getSubscriptionStart(),
                userSubscription.getSubscriptionDeadline(),
                remaining_days
        );
    }
}
