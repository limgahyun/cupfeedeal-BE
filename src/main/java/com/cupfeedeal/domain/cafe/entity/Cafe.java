package com.cupfeedeal.domain.cafe.entity;

import com.cupfeedeal.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Cafe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String addressLat;

    @Column(nullable = false)
    private String addressLng;

    @Column(nullable = false)
    private String operationTime;

    @Column(nullable = false)
    private Integer subscriptionPrice;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String snsAddress;

    @Column
    private Boolean isRecommended = false;

    @Column
    private Boolean isNewOpen = false;

    @Column
    private String signatureMenu;

    @PrePersist
    public void prePersist() {
        if (isRecommended == null) {
            isRecommended = false;
        } if (isNewOpen == null) {
            isNewOpen = false;
        }
    }

    @Builder
    public Cafe(String name, String address, String addressLat, String addressLng, String operationTime, Integer subscriptionPrice, String description, String phoneNumber, String snsAddress, Boolean isRecommended, Boolean isNewOpen, String signatureMenu) {
        this.name = name;
        this.address = address;
        this.addressLat = addressLat;
        this.addressLng = addressLng;
        this.operationTime = operationTime;
        this.subscriptionPrice = subscriptionPrice;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.snsAddress = snsAddress;
        this.isRecommended = isRecommended;
        this.isNewOpen = isNewOpen;
        this.signatureMenu = signatureMenu;
    }
}
