package com.cupfeedeal.domain.cafe.dto.request;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeImage.dto.request.CafeImageCreateRequestDto;

import java.util.ArrayList;
import java.util.List;

public record CafeCreateRequestDto (
        String name,
        String address,
        String address_latitude,
        String address_longitude,
        String operation_time,
        Integer subscription_price,
        String description,
        String phoneNumber,
        String SnsAddress,
        List<String> images
){
    public Cafe toEntity(){
        return Cafe.builder()
                .name(name)
                .address(address)
                .addressLat(address_latitude)
                .addressLng(address_longitude)
                .operationTime(operation_time)
                .subscriptionPrice(subscription_price)
                .description(description)
                .phoneNumber(phoneNumber)
                .snsAddress(SnsAddress)
                .build();
    }

    public List<CafeImageCreateRequestDto> toImageCreateRequestDto(Cafe cafe){
        List<CafeImageCreateRequestDto> imageCreateRequestDtos = new ArrayList<>();

        images.stream()
                .map(image -> imageCreateRequestDtos.add(CafeImageCreateRequestDto.from(image, cafe)));

        return imageCreateRequestDtos;
    }

}
