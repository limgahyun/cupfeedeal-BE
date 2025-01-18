package com.cupfeedeal.domain.cafeSubscriptionType.dto.response;

import java.util.List;

public record CafeSubscriptionInfoResponseDto (
        Long cafe_id,
        String cafe_name,
        List<CafeSubscriptionListResponseDto> cafe_subscriptions
){
    public static CafeSubscriptionInfoResponseDto from(Long cafeId, String cafeName, List<CafeSubscriptionListResponseDto> cafe_subscriptions) {
        return new CafeSubscriptionInfoResponseDto(
                cafeId,
                cafeName,
                cafe_subscriptions
        );
    }
}
