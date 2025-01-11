package com.cupfeedeal.domain.Auth.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtTokenResponseDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
