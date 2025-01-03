package com.cupfeedeal.domain.cafe.dto.request;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeImage.dto.request.CafeImageCreateRequestDto;

import java.util.List;

public record CafeCreateRequestDto (
        String name,
        String address,
        String address_map,
        String operation_time,
        String subscription_price,
        String description,
        String phoneNumber,
        String SnsAddress,
        List<CafeImageCreateRequestDto> images
){
    public Cafe toEntity(){
        return Cafe.builder()
                .name(name)
                .address(address)
                .snsAddress(address_map)
                .operationTime(operation_time)
                .subscriptionPrice(subscription_price)
                .description(description)
                .phoneNumber(phoneNumber)
                .snsAddress(SnsAddress)
                .build();
    }

}
