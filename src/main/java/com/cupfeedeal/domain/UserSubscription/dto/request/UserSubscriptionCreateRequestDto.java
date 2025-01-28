package com.cupfeedeal.domain.UserSubscription.dto.request;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.UserSubscription.enumerate.SubscriptionStatus;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserSubscriptionCreateRequestDto (
        Long cafeSubscriptionTypeId,
        LocalDate subscriptionStart
){
    public UserSubscription toEntity(User user, CafeSubscriptionType cafeSubscriptionType, LocalDateTime subscriptionDeadline, SubscriptionStatus subscriptionStatus) {
        return UserSubscription.builder()
                .user(user)
                .cafeSubscriptionType(cafeSubscriptionType)
                .subscriptionStart(subscriptionStart.atStartOfDay())
                .subscriptionDeadline(subscriptionDeadline)
                .extendedSubscriptionDeadline(subscriptionDeadline)
                .usingCount(0)
                .subscriptionStatus(subscriptionStatus)
                .build();
    }
}
