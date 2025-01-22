package com.cupfeedeal.domain.User.dto.response;

import com.cupfeedeal.domain.User.entity.User;

public record UserMainInfoResponseDto (
        Long userId,
        Integer subscription_count,
        String cupcatImgUrl
){
    public static UserMainInfoResponseDto from(User user, Integer subscription_count, String cupcatImgUrl) {
        return new UserMainInfoResponseDto(
                user.getUserId(),
                subscription_count,
                cupcatImgUrl
        );
    }
}
