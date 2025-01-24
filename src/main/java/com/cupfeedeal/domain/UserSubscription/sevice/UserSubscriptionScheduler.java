package com.cupfeedeal.domain.UserSubscription.sevice;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.User.repository.UserRepository;
import com.cupfeedeal.domain.UserSubscription.entity.UserSubscription;
import com.cupfeedeal.domain.UserSubscription.enumerate.SubscriptionStatus;
import com.cupfeedeal.domain.UserSubscription.repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserSubscriptionScheduler {

    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserSubscriptionService userSubscriptionService;
    private final UserRepository userRepository;

    // 매일 자정에 실행 (00:00:00)
    @Scheduled(cron = "0 0 0 * * *")
    public void setUserSubscriptions() {
        LocalDateTime now = LocalDateTime.now();

        // 모든 UserSubscription의 isUsed 값을 false로 설정
        resetIsUsedFalse();

        // 기간이 시작된 userSubscription
        setStatusValid(now);

        // 기간이 만료된 userSubscription
        setStatusExpired(now);
    }

    /*
    모든 userSubscription isUsed 초기화
     */
    public void resetIsUsedFalse() {
        userSubscriptionRepository.resetAllIsUsed();
        System.out.println("UserSubscription isUsed 값이 모두 false로 리셋되었습니다.");
    }

    /*
    기간이 시작된 userSubscription
     */
    public void setStatusValid(LocalDateTime now) {
        List<UserSubscription> valid_subscriptions = userSubscriptionRepository.findAllAfterSubscriptionStart(now);
        valid_subscriptions.forEach(userSubscription -> {
            // status VALID
            userSubscription.setSubscriptionStatus(SubscriptionStatus.VALID);
            userSubscriptionRepository.save(userSubscription);
        });
    }

    /*
    기간이 만료된 userSubscription
     */
    public void setStatusExpired(LocalDateTime now) {
        List<UserSubscription> expired_subscriptions = userSubscriptionRepository.findAllAfterSubscriptionDeadline(now);
        expired_subscriptions.forEach(userSubscription -> {
            // status EXPIRED 설정
            userSubscription.setSubscriptionStatus(SubscriptionStatus.EXPIRED);
            userSubscriptionRepository.save(userSubscription);

            // 해당 구독권으로 발자국을 얻은 경우 paw count - 1
            Boolean isGettingPaw = userSubscriptionService.isGettingPaw(userSubscription);
            if (isGettingPaw) {
                User user = userSubscription.getUser();
                user.setPawCount(user.getPawCount() - 1);
                userRepository.save(user);
            }
        });
    }
}