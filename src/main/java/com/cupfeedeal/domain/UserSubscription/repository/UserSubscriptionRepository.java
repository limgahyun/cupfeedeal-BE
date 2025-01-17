package com.cupfeedeal.domain.UserSubscription.repository;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {

    List<UserSubscription> findAllByUser(User user);
    //List<UserSubscription> findByUserId(Long userId);
    List<UserSubscription> findByUser_UserId(Long userId);
}
