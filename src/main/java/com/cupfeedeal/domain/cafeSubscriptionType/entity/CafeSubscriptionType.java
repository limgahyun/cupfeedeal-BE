package com.cupfeedeal.domain.cafeSubscriptionType.entity;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CafeSubscriptionType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_subscription_type_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer period;

    @Column(nullable = false)
    private Integer price;

    @Column
    private Float discount_percentage;

    @Column(nullable = false)
    private Integer original_drink_price;


    @Builder
    public CafeSubscriptionType(String name, Integer period, Integer price, Float discount_percentage, Integer original_drink_price) {
        this.name = name;
        this.period = period;
        this.price = price;
        this.discount_percentage = discount_percentage;
        this.original_drink_price = original_drink_price;
    }
}