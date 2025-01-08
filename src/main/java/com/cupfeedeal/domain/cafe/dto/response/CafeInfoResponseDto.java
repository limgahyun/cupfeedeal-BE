package com.cupfeedeal.domain.cafe.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;

import java.util.List;

public record CafeInfoResponseDto (
        Long id,
        String address_map,
        String address,
        List<CafeSubscriptionListResponseDto> subscription_types,
        List<String> images,
        String operation_time,
        String phone_num,
        String sns_address,
        String description,
        String menu_board,
        Boolean is_like,
        Boolean is_subscription
) {
    public static CafeInfoResponseDto from(Cafe cafe,
                                           List<CafeSubscriptionListResponseDto> subscription_types,
                                           List<String> images,
                                           Boolean is_like,
                                           Boolean is_subscription) {
        return new CafeInfoResponseDto(
                cafe.getId(),
                cafe.getAddressMap(),
                cafe.getAddress(),
                subscription_types,
                images,
                cafe.getOperationTime(),
                cafe.getPhoneNumber(),
                cafe.getSnsAddress(),
                cafe.getDescription(),
                cafe.getMenuBoard(),
                is_like,
                is_subscription
        );
    }
}
