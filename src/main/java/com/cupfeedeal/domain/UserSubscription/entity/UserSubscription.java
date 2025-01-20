package com.cupfeedeal.domain.UserSubscription.entity;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.UserSubscription.enumerate.SubscriptionStatus;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import com.cupfeedeal.global.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.security.Timestamp;
import java.time.LocalDate;
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

    @Column(name = "isUsed", nullable = false)
    private Boolean isUsed;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status", nullable = false)
    private SubscriptionStatus subscriptionStatus;
}
