package com.cupfeedeal.domain.User.dto.response;

import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;

public record CupcatInfoResponseDto (
        String cafe_name,
        Long cupcat_id
){
    public static CupcatInfoResponseDto from(UserCupcat userCupcat) {
        return new CupcatInfoResponseDto(
                userCupcat.getCafeName(),
                userCupcat.getCupcat().getCupcatId()
        );
    }
}
