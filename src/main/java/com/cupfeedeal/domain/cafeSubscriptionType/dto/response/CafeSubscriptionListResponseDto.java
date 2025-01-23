package com.cupfeedeal.domain.cafeSubscriptionType.dto.response;

import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;

public record CafeSubscriptionListResponseDto(
    Long subscription_id,
    String menu,
    Integer period,
    Integer price
){
    public static CafeSubscriptionListResponseDto from(CafeSubscriptionType subscription) {
        return new CafeSubscriptionListResponseDto(
                subscription.getId(),
                "아이스 아메리카노",
                subscription.getPeriod() / 7,
                subscription.getPrice()
        );
    }
}
