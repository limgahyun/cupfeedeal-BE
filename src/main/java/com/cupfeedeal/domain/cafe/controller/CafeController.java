package com.cupfeedeal.domain.cafe.controller;

import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.cafe.dto.request.CafeCreateRequestDto;
import com.cupfeedeal.domain.cafe.dto.response.CafeInfoResponseDto;
import com.cupfeedeal.domain.cafe.dto.response.CafeListResponseDto;
import com.cupfeedeal.domain.cafe.dto.response.CafeNewOpenListResponseDto;
import com.cupfeedeal.domain.cafe.dto.response.CafeRecommendationListResponseDto;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafe.service.CafeService;
import com.cupfeedeal.global.common.annotation.Login;
import com.cupfeedeal.global.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "카페 상세 정보 조회")
    @GetMapping("/{cafeId}")
    public CommonResponse<CafeInfoResponseDto> getCafeInfo(@PathVariable Long cafeId, @Login User user) {
        CafeInfoResponseDto cafeInfo = cafeService.getCafeInfo(cafeId, user);
        return new CommonResponse<>(cafeInfo, "해당 카페의 상세 정보 조회에 성공하였습니다.");
    }

    @Operation(summary = "카페 검색 결과 리스트 조회")
    @GetMapping
    public CommonResponse<List<CafeListResponseDto>> getCafeList(
            @RequestParam(value = "search", defaultValue = "") String name,
            @Login User user,
            @RequestParam(value = "like", defaultValue = "false") Boolean isLike ) {
        List<CafeListResponseDto> cafeList = cafeService.getCafeList(name, user, isLike);
        return new CommonResponse<>(cafeList, "카페 리스트 조회에 성공하였습니다.");
    }

}
