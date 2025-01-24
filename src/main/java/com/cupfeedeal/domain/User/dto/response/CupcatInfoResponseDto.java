package com.cupfeedeal.domain.User.dto.response;

import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;

public record CupcatInfoResponseDto (
        String cafe_name,
        String cupcat_img_url
){
    public static CupcatInfoResponseDto from(UserCupcat userCupcat) {
        return new CupcatInfoResponseDto(
                userCupcat.getCafeName(),
                userCupcat.getCupcat().getImageUrl()
        );
    }
}
