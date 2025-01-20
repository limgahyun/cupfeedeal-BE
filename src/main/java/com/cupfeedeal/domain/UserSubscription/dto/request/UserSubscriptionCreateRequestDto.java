package com.cupfeedeal.domain.UserSubscription.dto.request;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;

import java.time.LocalDateTime;

import static com.cupfeedeal.domain.UserSubscription.enumerate.SubscriptionStatus.VALID;

public record UserSubscriptionCreateRequestDto (
        Long cafeSubscriptionTypeId,
        LocalDateTime subscriptionStart,
        LocalDateTime subscriptionDeadline
){
    public UserSubscription toEntity(User user, CafeSubscriptionType cafeSubscriptionType) {
        return UserSubscription.builder()
                .user(user)
                .cafeSubscriptionType(cafeSubscriptionType)
                .subscriptionStart(subscriptionStart)
                .subscriptionDeadline(subscriptionDeadline)
                .usingCount(0)
                .subscriptionStatus(VALID)
                .build();
    }
}
