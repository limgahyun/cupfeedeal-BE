package com.cupfeedeal.domain.cafe.dto.response;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeImage.dto.response.CafeImageResponseDto;

import java.util.List;

public record CafeInfoResponseDto (
        Long id,
        String name,
        String address,
        String address_lat,
        String address_lng,
        List<CafeImageResponseDto> images,
        String operation_time,
        String phone_num,
        String sns_address,
        String description,
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
                cafe.getAddress(),
                cafe.getAddressLat(),
                cafe.getAddressLng(),
                images,
                cafe.getOperationTime(),
                cafe.getPhoneNumber(),
                cafe.getSnsAddress(),
                cafe.getDescription(),
                is_like,
                is_subscription
        );
    }
}
