package com.cupfeedeal.domain.Auth.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String username;
    private String token;
    private Boolean is_first;
    private Integer subscription_count;

}
