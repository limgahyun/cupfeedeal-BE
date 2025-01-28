package com.cupfeedeal.domain.User.dto.response;

import com.cupfeedeal.domain.User.entity.User;

import java.util.List;

public record UserCupcatInfoResponseDto (
        Integer level,
        List<CupcatInfoResponseDto> cupcats
){
    public static UserCupcatInfoResponseDto from(User user, List<CupcatInfoResponseDto> cupcats) {
        return new UserCupcatInfoResponseDto(
                user.getUser_level(),
                cupcats
        );
    }
}
