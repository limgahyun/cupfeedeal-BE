package com.cupfeedeal.domain.cafeSubscriptionType.entity;

import com.cupfeedeal.domain.cafeSubscriptionType.enumerate.SubscriptionMenu;
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

    @Column(nullable = false)
    private String name;

    @Column
    private String cafe_name;

    @Column(nullable = false)
    private SubscriptionMenu menu;

    @Column(nullable = false)
    private Integer period;

    @Column(nullable = false)
    private Integer price;

    @Column
    private Float discount_percentage;

    @Column(nullable = false)
    private Integer original_drink_price;


    @Builder
    public CafeSubscriptionType(String name, String cafe_name, SubscriptionMenu menu, Integer period, Integer price, Float discount_percentage, Integer original_drink_price) {
        this.name = name;
        this.cafe_name = cafe_name;
        this.menu = menu;
        this.period = period;
        this.price = price;
        this.discount_percentage = discount_percentage;
        this.original_drink_price = original_drink_price;
    }
}