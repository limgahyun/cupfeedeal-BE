package com.cupfeedeal.domain.cafeSubscriptionType.dto.response;

import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;

public record CafeSubscriptionListResponseDto(
    Long subscription_id,
    String subscription_name,
    Integer period,
    Integer price
){
    public static CafeSubscriptionListResponseDto from(CafeSubscriptionType subscription) {
        return new CafeSubscriptionListResponseDto(
                subscription.getId(),
                subscription.getName(),
                subscription.getPeriod() / 7,
                subscription.getPrice()
        );
    }
}
