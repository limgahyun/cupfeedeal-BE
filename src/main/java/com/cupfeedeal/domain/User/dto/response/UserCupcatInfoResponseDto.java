package com.cupfeedeal.domain.User.dto.response;

import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;

import java.time.LocalDate;

public record UserCupcatInfoResponseDto (
        String cafe_name,
        LocalDate birth_date,
        Integer level,
        String cupcat_img_url
){
    public static UserCupcatInfoResponseDto from(UserCupcat userCupcat, String cafe_name) {
        return new UserCupcatInfoResponseDto(
                cafe_name,
                userCupcat.getCreatedAt().toLocalDate(),
                userCupcat.getUser().getUser_level(),
                userCupcat.getCupcat().getImageUrl()
        );
    }
}
