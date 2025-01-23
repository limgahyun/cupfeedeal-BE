package com.cupfeedeal.domain.User.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoHomeResponseDto {
    public Integer subscription_count;
    private String cupcatImgUrl;
}
