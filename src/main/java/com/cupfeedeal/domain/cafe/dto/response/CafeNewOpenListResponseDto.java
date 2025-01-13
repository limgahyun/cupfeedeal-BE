package com.cupfeedeal.domain.cafe.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;

public record CafeNewOpenListResponseDto (
        Long cafe_id,
        String name,
        String description
){
    public static CafeNewOpenListResponseDto from(Cafe cafe) {
        return new CafeNewOpenListResponseDto(
                cafe.getId(),
                cafe.getName(),
                cafe.getDescription()
        );
    }
}
