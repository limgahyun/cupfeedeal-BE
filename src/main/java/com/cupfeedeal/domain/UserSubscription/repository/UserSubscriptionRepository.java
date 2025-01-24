package com.cupfeedeal.domain.UserSubscription.repository;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.UserSubscription.enumerate.SubscriptionStatus;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    List<UserSubscription> findAllByUser(User user);

    @Query("SELECT us FROM UserSubscription us WHERE us.user = :user AND us.subscriptionStatus = 'VALID'")
    List<UserSubscription> findByUserAndSubscriptionStatusIsValid(@Param("user") User user);

    @Query("SELECT count(us) FROM UserSubscription us WHERE us.user = :user AND us.subscriptionStatus = 'VALID'")
    Integer countByUserAndSubscriptionStatusIsValid(@Param("user") User user);

    @Query("SELECT count(us) FROM UserSubscription us WHERE us.user = :user AND us.subscriptionStatus in :status")
    Integer countByUserAndSubscriptionStatusIsValidOrNotYet(@Param("user")User user, @Param("status") List<SubscriptionStatus> status);

    //List<UserSubscription> findByUserId(Long userId);
    List<UserSubscription> findByUser_UserId(Long userId);

    Optional<UserSubscription> findByUserAndCafeSubscriptionType(User user, CafeSubscriptionType cafeSubscriptionType);

    @Modifying
    @Transactional
    @Query("UPDATE UserSubscription u SET u.isUsed = false")
    void resetAllIsUsed();

    @Query("SELECT us FROM UserSubscription us WHERE us.subscriptionStart >= :now AND us.subscriptionStatus = 'NOT_YET'")
    List<UserSubscription> findAllAfterSubscriptionStart(@Param("now") LocalDateTime now);

    @Query("SELECT us FROM UserSubscription us WHERE us.subscriptionDeadline <= :now AND us.subscriptionStatus = 'VALID'")
    List<UserSubscription> findAllAfterSubscriptionDeadline(@Param("now") LocalDateTime now);
}
