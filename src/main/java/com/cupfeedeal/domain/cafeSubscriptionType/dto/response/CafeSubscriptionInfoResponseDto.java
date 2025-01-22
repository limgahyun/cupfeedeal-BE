package com.cupfeedeal.domain.cafeSubscriptionType.dto.response;

import com.cupfeedeal.domain.UserSubscription.dto.response.UserSubscriptionInfoResponseDto;
import com.cupfeedeal.domain.cafe.entity.Cafe;

import java.util.List;

public record CafeSubscriptionInfoResponseDto (
        Long cafe_id,
        String cafe_name,
        String menu,
        List<Integer> periods,
        UserSubscriptionInfoResponseDto userSubscriptionInfo,
        List<CafeSubscriptionListResponseDto> cafe_subscriptions
){
    public static CafeSubscriptionInfoResponseDto from(Cafe cafe, UserSubscriptionInfoResponseDto userSubscriptionInfo, List<CafeSubscriptionListResponseDto> cafe_subscriptions) {
        return new CafeSubscriptionInfoResponseDto(
                cafe.getId(),
                cafe.getName(),
                "아이스 아메리카노",
                List.of(2, 4),
                userSubscriptionInfo,
                cafe_subscriptions
        );
    }
}
