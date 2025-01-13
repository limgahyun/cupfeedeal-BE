package com.cupfeedeal.domain.cafe.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeImage.entity.CafeImage;

public record CafeListResponseDto (
        Long id,
        String name,
        String address_lat,
        String address_lng,
        String address,
        Integer price,
        String image_url,
        Boolean is_like
) {
    public static CafeListResponseDto from(Cafe cafe,
                                           CafeImage image,
                                           Boolean is_like) {
        return new CafeListResponseDto(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddressLat(),
                cafe.getAddressLng(),
                cafe.getAddress(),
                cafe.getSubscriptionPrice(),
                image.getImageUrl(),
                is_like
        );
    }
}

