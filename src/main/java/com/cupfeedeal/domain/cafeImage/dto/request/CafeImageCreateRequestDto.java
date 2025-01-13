package com.cupfeedeal.domain.cafeImage.dto.request;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeImage.entity.CafeImage;

public record CafeImageCreateRequestDto (
        String imageUrl,
        Cafe cafe
){
    public static CafeImageCreateRequestDto from(String imageUrl, Cafe cafe) {
        return new CafeImageCreateRequestDto(
                imageUrl,
                cafe
        );
    }

    public CafeImage toEntity(){
        return CafeImage.builder()
                .cafe(cafe)
                .imageUrl(imageUrl)
                .isMainImage(false)
                .build();
    }
}
