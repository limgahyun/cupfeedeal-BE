package com.cupfeedeal.domain.cafeSubscriptionType.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;

import java.util.List;

public record CafeSubscriptionInfoResponseDto (
        Long cafe_id,
        String cafe_name,
        List<CafeSubscriptionListResponseDto> cafe_subscriptions
){
    public static CafeSubscriptionInfoResponseDto from(Cafe cafe, List<CafeSubscriptionListResponseDto> cafe_subscriptions) {
        return new CafeSubscriptionInfoResponseDto(
                cafe.getCafeId(),
                cafe.getName(),
                cafe_subscriptions
        );
    }
}
