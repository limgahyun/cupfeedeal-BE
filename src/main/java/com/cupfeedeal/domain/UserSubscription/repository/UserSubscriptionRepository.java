package com.cupfeedeal.domain.UserSubscription.repository;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    List<UserSubscription> findAllByUser(User user);
    //List<UserSubscription> findByUserId(Long userId);
    List<UserSubscription> findByUser_UserId(Long userId);

    Optional<UserSubscription> findByUserAndCafeSubscriptionType(User user, CafeSubscriptionType cafeSubscriptionType);
}
