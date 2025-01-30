package com.cupfeedeal.domain.UserSubscription.dto.response;

public record UserSubscriptionUseResponseDto (
        Boolean is_getting_paw,
        Double saved_cups,
        Integer paw_count
){
    public static UserSubscriptionUseResponseDto from(Boolean getting_paw, Double saved_cups, Integer paw_count) {
        return new UserSubscriptionUseResponseDto(
                getting_paw,
                saved_cups,
                paw_count
        );
    }
}
