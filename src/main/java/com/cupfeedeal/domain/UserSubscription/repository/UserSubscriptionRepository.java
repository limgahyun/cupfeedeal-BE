package com.cupfeedeal.domain.UserSubscription.repository;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.UserSubscription.enumerate.SubscriptionStatus;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    List<UserSubscription> findAllByUser(User user);

    @Query("SELECT us FROM UserSubscription us WHERE us.user = :user AND us.subscriptionStatus = :status")
    List<UserSubscription> findByUserAndSubscriptionStatusIsValid(@Param("user") User user, @Param("status") SubscriptionStatus status);
    //List<UserSubscription> findByUserId(Long userId);
    List<UserSubscription> findByUser_UserId(Long userId);

    Optional<UserSubscription> findByUserAndCafeSubscriptionType(User user, CafeSubscriptionType cafeSubscriptionType);
}
