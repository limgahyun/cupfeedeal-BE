package com.cupfeedeal.domain.cafe.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeImage.dto.response.CafeImageResponseDto;
import com.cupfeedeal.domain.cafeSubscriptionType.dto.response.CafeSubscriptionTypeResponseDto;

import java.util.List;

public record CafeInfoResponseDto (
        Long id,
        String name,
        String address_map,
        String address,
        List<CafeImageResponseDto> images,
        String operation_time,
        String phone_num,
        String sns_address,
        String description,
        String menu_board,
        Boolean is_like,
        Boolean is_subscription
) {
    public static CafeInfoResponseDto from(Cafe cafe,
                                           List<CafeImageResponseDto> images,
                                           Boolean is_like,
                                           Boolean is_subscription) {
        return new CafeInfoResponseDto(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddressMap(),
                cafe.getAddress(),
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
