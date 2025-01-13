package com.cupfeedeal.domain.cafeSubscriptionType.dto.response;

import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;

public record CafeSubscriptionListResponseDto(
    Long subscription_id,
    String subscription_name
){
    public static CafeSubscriptionListResponseDto from(CafeSubscriptionType subscription) {
        return new CafeSubscriptionListResponseDto(
                subscription.getId(),
                subscription.getName()
        );
    }
}
