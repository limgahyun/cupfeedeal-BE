package com.cupfeedeal.domain.UserSubscription.dto.response;

import com.cupfeedeal.domain.User.entity.User;

public record UserSubscriptionCancelResponseDto (
        Integer paw_count
){
    public static UserSubscriptionCancelResponseDto from(User user) {
        return new UserSubscriptionCancelResponseDto(
                user.getPawCount()
        );
    }
}

