package com.cupfeedeal.domain.UserSubscription.dto.response;

import java.util.List;

public record UserSubscriptionValidListResponseDto (
        Integer paw_count,
        List<UserSubscriptionListResponseDto> userSubscriptionListResponseDtos
){
    public static UserSubscriptionValidListResponseDto from(Integer paw_count,
                                                            List<UserSubscriptionListResponseDto> userSubscriptionListResponseDtos) {
        return new UserSubscriptionValidListResponseDto(
                paw_count,
                userSubscriptionListResponseDtos
        );
    }
}
