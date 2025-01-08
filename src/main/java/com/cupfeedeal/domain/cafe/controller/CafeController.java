package com.cupfeedeal.domain.cafe.controller;

import com.cupfeedeal.domain.cafe.dto.request.CafeCreateRequestDto;
import com.cupfeedeal.domain.cafe.dto.response.CafeNewOpenListResponseDto;
import com.cupfeedeal.domain.cafe.dto.response.CafeRecommendationListResponseDto;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafe.service.CafeService;
import com.cupfeedeal.global.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Operation(summary = "추천 카페 조회")
    @GetMapping("/recommendation")
    public CommonResponse<List<CafeRecommendationListResponseDto>> getRecommendations() {
        List<CafeRecommendationListResponseDto> recommendationList = cafeService.getRecommendationCafes();
        return new CommonResponse<>(recommendationList, "추천 카페 조회에 성공하였습니다.");
    }

    @Operation(summary = "추천 카페 조회")
    @GetMapping("/new")
    public CommonResponse<List<CafeNewOpenListResponseDto>> getOpenCafes() {
        List<CafeNewOpenListResponseDto> openCafeList = cafeService.getNewOpenCafes();
        return new CommonResponse<>(openCafeList, "새로 오픈 카페 조회에 성공하였습니다.");
    }


}
