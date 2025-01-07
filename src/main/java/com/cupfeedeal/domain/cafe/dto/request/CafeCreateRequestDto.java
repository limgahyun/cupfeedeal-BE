package com.cupfeedeal.domain.cafe.dto.request;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeImage.dto.request.CafeImageCreateRequestDto;

import java.util.ArrayList;
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
        String menuBoard,
        List<String> images
){
    public Cafe toEntity(){
        return Cafe.builder()
                .name(name)
                .address(address)
                .addressMap(address_map)
                .snsAddress(address_map)
                .operationTime(operation_time)
                .subscriptionPrice(subscription_price)
                .description(description)
                .phoneNumber(phoneNumber)
                .snsAddress(SnsAddress)
                .menuBoard(menuBoard)
                .build();
    }

    public List<CafeImageCreateRequestDto> toImageCreateRequestDto(Cafe cafe){
        List<CafeImageCreateRequestDto> imageCreateRequestDtos = new ArrayList<>();

        images.stream()
                .map(image -> imageCreateRequestDtos.add(CafeImageCreateRequestDto.from(image, cafe)));

        return imageCreateRequestDtos;
    }

}
