package com.cupfeedeal.domain.UserSubscription.entity;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.UserSubscription.enumerate.SubscriptionStatus;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import com.cupfeedeal.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user_subscription")
public class UserSubscription extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_subscription_id", unique = true, updatable = false)
    private Long userSubscriptionId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cafe_subscription_type_id", nullable = false)
    private CafeSubscriptionType cafeSubscriptionType;

    @Column(name = "subscription_start", nullable = false)
    private LocalDateTime subscriptionStart;

    @Column(name = "subscription_deadline", nullable = false)
    private LocalDateTime subscriptionDeadline;

    @Column(name = "extended_subscription_deadline")
    private LocalDateTime extendedSubscriptionDeadline;

    @Column(name = "isUsed", nullable = false)
    private Boolean isUsed = false;

    @Column(name = "using_count", nullable = false)
    private Integer usingCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status", nullable = false)
    private SubscriptionStatus subscriptionStatus;

    @PrePersist
    public void prePersist() {
        if (isUsed == null) {
            isUsed = false;
        }
        if (extendedSubscriptionDeadline == null) {
            extendedSubscriptionDeadline = subscriptionDeadline;
        }
    }

    @Builder
    public UserSubscription(User user, CafeSubscriptionType cafeSubscriptionType, LocalDateTime subscriptionStart, LocalDateTime subscriptionDeadline, Integer usingCount, SubscriptionStatus subscriptionStatus) {
        this.user = user;
        this.cafeSubscriptionType = cafeSubscriptionType;
        this.subscriptionStart = subscriptionStart;
        this.subscriptionDeadline = subscriptionDeadline;
        this.extendedSubscriptionDeadline = subscriptionDeadline;
        this.usingCount = usingCount;
        this.subscriptionStatus = subscriptionStatus;
    }
}
