package com.cupfeedeal.domain.cafe.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;

import java.util.List;

public record CafeRecommendationListResponseDto (
        Long cafe_id,
        String name,
        List<String> menu,
        String address,
        String subscription_price
){
    public static CafeRecommendationListResponseDto from(Cafe cafe, List<String> menu) {
        return new CafeRecommendationListResponseDto(
                cafe.getId(),
                cafe.getName(),
                menu,
                cafe.getAddress(),
                cafe.getSubscriptionPrice()
        );
    }
}
