package com.cupfeedeal.domain.cafe.controller;

import com.cupfeedeal.domain.cafe.dto.request.CafeCreateRequestDto;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafe.service.CafeService;
import com.cupfeedeal.global.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cafe", description = "cafe api")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cafe")
public class CafeController {

    private final CafeService cafeService;

    @Operation(summary = "cafe 생성")
    @PostMapping
    public CommonResponse<Void> createCafe(CafeCreateRequestDto cafe) {
        cafeService.createCafe(cafe);
        return new CommonResponse<>("new cafe 생성에 성공하였습니다.");
    }
}
