package com.cupfeedeal.domain.cafe.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeImage.entity.CafeImage;

public record CafeNewOpenListResponseDto (
        Long cafe_id,
        String name,
        String address,
        String image_url
){
    public static CafeNewOpenListResponseDto from(Cafe cafe, CafeImage image) {
        return new CafeNewOpenListResponseDto(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress(),
                image.getImageUrl()
        );
    }
}
