package com.cupfeedeal.domain.User.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponseDto {
    public String username;
    public Integer user_level;
    public String cupcatImgUrl;
    public String cafe_name;
    public LocalDate birth_date;
}
