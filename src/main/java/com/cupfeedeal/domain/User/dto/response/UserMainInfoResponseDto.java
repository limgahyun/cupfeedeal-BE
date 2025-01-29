package com.cupfeedeal.domain.User.dto.response;

import com.cupfeedeal.domain.User.entity.User;

public record UserMainInfoResponseDto (
        Long userId,
        Integer subscription_count,
        Long cupcat_id
){
    public static UserMainInfoResponseDto from(User user, Integer subscription_count, Long cupcat_id) {
        return new UserMainInfoResponseDto(
                user.getUserId(),
                subscription_count,
                cupcat_id
        );
    }
}
