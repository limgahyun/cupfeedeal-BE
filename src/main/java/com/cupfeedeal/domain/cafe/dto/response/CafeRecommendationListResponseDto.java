package com.cupfeedeal.domain.cafe.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;

public record CafeRecommendationListResponseDto (
        Long cafe_id,
        String name,
        String menu,
        String address,
        String subscription_price
){
    public static CafeRecommendationListResponseDto from(Cafe cafe) {
        return new CafeRecommendationListResponseDto(
                cafe.getId(),
                cafe.getName(),
                cafe.getSignitureMenu(),
                cafe.getAddress(),
                cafe.getSubscriptionPrice()
        );
    }
}
