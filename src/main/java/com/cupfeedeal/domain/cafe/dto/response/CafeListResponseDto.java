package com.cupfeedeal.domain.cafe.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeSubscriptionType.dto.response.CafeSubscriptionTypeResponseDto;

import java.util.List;

public record CafeListResponseDto (
        Long id,
        String name,
        String address_map,
        String address,
        String signiture_menu,
        Integer price,
        String images,
        Boolean is_like,
        Boolean is_subscription
) {
    public static CafeListResponseDto from(Cafe cafe,
                                           List<CafeSubscriptionTypeResponseDto> subscription_types,
                                           String image,
                                           Boolean is_like,
                                           Boolean is_subscription) {
        return new CafeListResponseDto(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddressMap(),
                cafe.getAddress(),
                cafe.getSignitureMenu(),
                cafe.getSubscriptionPrice(),
                image,
                is_like,
                is_subscription
        );
    }
}

