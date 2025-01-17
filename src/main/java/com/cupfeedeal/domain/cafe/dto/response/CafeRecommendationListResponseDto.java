package com.cupfeedeal.domain.cafe.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;

public record CafeRecommendationListResponseDto (
        Long cafe_id,
        String name,
        String menu,
        String address,
        Integer subscription_price
){
    public static CafeRecommendationListResponseDto from(Cafe cafe) {
        return new CafeRecommendationListResponseDto(
                cafe.getCafeId(),
                cafe.getName(),
                cafe.getSignatureMenu(),
                cafe.getAddress(),
                cafe.getSubscriptionPrice()
        );
    }
}
