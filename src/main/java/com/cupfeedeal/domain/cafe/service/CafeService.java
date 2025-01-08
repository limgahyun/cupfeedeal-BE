package com.cupfeedeal.domain.cafe.service;

import com.cupfeedeal.domain.cafe.dto.request.CafeCreateRequestDto;
import com.cupfeedeal.domain.cafe.dto.response.CafeNewOpenListResponseDto;
import com.cupfeedeal.domain.cafe.dto.response.CafeRecommendationListResponseDto;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafe.repository.CafeRepository;
import com.cupfeedeal.domain.cafeImage.dto.request.CafeImageCreateRequestDto;
import com.cupfeedeal.domain.cafeImage.service.CafeImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    private final CafeImageService cafeImageService;

    /*
    cafe 생성
     */
    @Transactional
    public void createCafe(CafeCreateRequestDto requestDto) {
        // cafe 저장
        final Cafe cafe = requestDto.toEntity();
        cafeRepository.save(cafe);

        System.out.println(requestDto.images());

        // imageUrls를 cafeImage entity로 변환하여 저장
        List<String> imageUrls = requestDto.images();

        imageUrls.forEach(imageUrl -> {
            cafeImageService.createCafeImage(CafeImageCreateRequestDto.from(imageUrl, cafe));
        });
    }

    /*
    추천 cafe 조회
     */
    public List<CafeRecommendationListResponseDto> getRecommendationCafes() {
        final List<Cafe> cafeList = cafeRepository.findTop3ByIsRecommendedIsTrueOrderByCreatedAtDesc();
        return cafeList.stream()
                .map(CafeRecommendationListResponseDto::from)
                .toList();
    }

    /*
    새로 오픈한 cafe 조회
     */
    public List<CafeNewOpenListResponseDto> getNewOpenCafes() {
        final List<Cafe> cafeList = cafeRepository.findTop3ByIsNewOpenIsTrueOrderByCreatedAtDesc();
        return cafeList.stream()
                .map(CafeNewOpenListResponseDto::from)
                .toList();
    }
}
