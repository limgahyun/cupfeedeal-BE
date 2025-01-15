package com.cupfeedeal.domain.User.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponseDto {
    private String username;
    private Integer user_level;
    private String cupcatImgUrl;
}
