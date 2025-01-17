package com.cupfeedeal.domain.UserSubscription.entity;

import com.cupfeedeal.domain.User.entity.User;
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
public class UserSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_subscription_id", unique = true, updatable = false)
    private Long userSubscriptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(name = "cafe_subscription_type_id", nullable = false)
    private Long cafeSubscriptionTypeId;

    @Column(name = "subscription_start", nullable = false)
    private LocalDateTime subscriptionStart;

    @Column(name = "subscription_deadline", nullable = false)
    private LocalDateTime subscriptionDeadline;

    @Column(name = "isUsed", nullable = false)
    private Boolean isUsed;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_status", nullable = false)
    private SubscriptionStatus subscriptionStatus;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public enum SubscriptionStatus {
        CANCELED, EXPIRED, VALID
    }

}
