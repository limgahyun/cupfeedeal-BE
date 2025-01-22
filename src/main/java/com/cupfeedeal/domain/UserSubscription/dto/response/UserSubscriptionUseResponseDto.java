package com.cupfeedeal.domain.UserSubscription.dto.response;

public record UserSubscriptionUseResponseDto (
        Boolean is_getting_paw
){
    public static UserSubscriptionUseResponseDto from(Boolean getting_paw) {
        return new UserSubscriptionUseResponseDto(
                getting_paw
        );
    }
}
