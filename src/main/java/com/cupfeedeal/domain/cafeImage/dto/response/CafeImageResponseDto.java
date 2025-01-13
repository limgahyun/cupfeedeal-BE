package com.cupfeedeal.domain.cafeImage.dto.response;

import com.cupfeedeal.domain.cafeImage.entity.CafeImage;

public record CafeImageResponseDto (
        String image_url
){
    public static CafeImageResponseDto from(CafeImage cafeImage) {
        return new CafeImageResponseDto(
                cafeImage.getImageUrl()
        );
    }
}
