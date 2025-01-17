package com.cupfeedeal.domain.cafe.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeImage.entity.CafeImage;

public record CafeRecommendationListResponseDto (
        Long cafe_id,
        String name,
        String address,
        Integer subscription_price,
        String image_url
){
    public static CafeRecommendationListResponseDto from(Cafe cafe, CafeImage image) {
        return new CafeRecommendationListResponseDto(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress(),
                cafe.getSubscriptionPrice(),
                image.getImageUrl()
        );
    }
}
