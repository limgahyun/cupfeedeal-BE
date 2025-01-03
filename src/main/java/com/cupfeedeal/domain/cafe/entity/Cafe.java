package com.cupfeedeal.domain.cafe.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String address_map;

    @Column(nullable = false)
    private String operation_time;

    @Column(nullable = false)
    private String subscription_price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String SnsAddress;

    @Builder
    public Cafe(String name, String address, String address_map, String operation_time, String subscription_price, String description, String phoneNumber, String SnsAddress) {
        this.name = name;
        this.address = address;
        this.address_map = address_map;
        this.operation_time = operation_time;
        this.subscription_price = subscription_price;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.SnsAddress = SnsAddress;
    }
}
