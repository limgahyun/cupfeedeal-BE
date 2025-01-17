package com.cupfeedeal.domain.User.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoUpdateRequestDto {
    public Long userId;
    public String username;
}
