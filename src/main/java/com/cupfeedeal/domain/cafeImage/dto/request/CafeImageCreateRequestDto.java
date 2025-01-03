package com.cupfeedeal.domain.cafeImage.dto.request;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeImage.entity.CafeImage;

public record CafeImageCreateRequestDto (
        String imageUrl
){
    public CafeImage toEntity(Cafe cafe){
        return CafeImage.builder()
                .cafe(cafe)
                .imageUrl(imageUrl)
                .build();
    }
}
