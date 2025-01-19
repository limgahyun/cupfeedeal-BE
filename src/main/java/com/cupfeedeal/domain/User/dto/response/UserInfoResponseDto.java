package com.cupfeedeal.domain.User.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponseDto {
    public String username;
    public Integer user_level;
    public String cupcatImgUrl;
}
