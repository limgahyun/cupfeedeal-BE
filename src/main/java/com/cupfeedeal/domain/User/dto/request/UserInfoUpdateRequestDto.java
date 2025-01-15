package com.cupfeedeal.domain.User.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoUpdateRequestDto {
    private Long userId;
    private String username;
}
