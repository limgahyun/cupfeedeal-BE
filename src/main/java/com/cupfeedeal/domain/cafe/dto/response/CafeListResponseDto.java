package com.cupfeedeal.domain.cafe.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;

public record CafeListResponseDto (
        Long id,
        String name,
        String address_map,
        String address,
        String signature_menu,
        Integer price,
        String images,
        Boolean is_like,
        Boolean is_subscription
) {
    public static CafeListResponseDto from(Cafe cafe,
                                           String image,
                                           Boolean is_like,
                                           Boolean is_subscription) {
        return new CafeListResponseDto(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddressMap(),
                cafe.getAddress(),
                cafe.getSignatureMenu(),
                cafe.getSubscriptionPrice(),
                image,
                is_like,
                is_subscription
        );
    }
}

