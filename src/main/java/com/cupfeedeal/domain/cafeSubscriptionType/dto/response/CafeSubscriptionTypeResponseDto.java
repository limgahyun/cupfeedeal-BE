package com.cupfeedeal.domain.cafeSubscriptionType.dto.response;

import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;

import java.util.List;

public record CafeSubscriptionTypeResponseDto(
    List<String> menu,
    List<String> period
){
    public static CafeSubscriptionTypeResponseDto from(List<String> menu,
                                                       List<String> period) {
        return new CafeSubscriptionTypeResponseDto(
                menu,
                period
        );
    }
}
