package com.cupfeedeal.domain.User.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoUpdateResponseDto {
    private String username;
    private Integer subscription_count;
    private String token;
}
