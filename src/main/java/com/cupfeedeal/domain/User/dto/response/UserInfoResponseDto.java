package com.cupfeedeal.domain.User.dto.response;

import com.cupfeedeal.domain.Cupcat.entity.UserCupcat;
import com.cupfeedeal.domain.User.entity.User;

import java.time.LocalDate;

public record UserInfoResponseDto (
    String username,
    Integer user_level,
    String cupcatImgUrl,
    String cafe_name,
    LocalDate birth_date

){
    public static UserInfoResponseDto from(User user, UserCupcat userCupcat) {
        return new UserInfoResponseDto(
                user.getUsername(),
                user.getUser_level(),
                userCupcat == null ? null : userCupcat.getCupcat().getImageUrl(),
                userCupcat == null ? null : userCupcat.getCafeName(),
                userCupcat == null ? null : userCupcat.getCreatedAt().toLocalDate()
        );
    }
}
