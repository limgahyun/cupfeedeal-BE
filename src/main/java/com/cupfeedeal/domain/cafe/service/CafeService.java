package com.cupfeedeal.domain.cafe.service;

import com.cupfeedeal.domain.cafe.dto.request.CafeCreateRequestDto;
import com.cupfeedeal.domain.cafe.dto.response.CafeInfoResponseDto;
import com.cupfeedeal.domain.cafe.dto.response.CafeListResponseDto;
import com.cupfeedeal.domain.cafe.dto.response.CafeNewOpenListResponseDto;
import com.cupfeedeal.domain.cafe.dto.response.CafeRecommendationListResponseDto;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafe.repository.CafeRepository;
import com.cupfeedeal.domain.cafeImage.dto.request.CafeImageCreateRequestDto;
import com.cupfeedeal.domain.cafeImage.dto.response.CafeImageResponseDto;
import com.cupfeedeal.domain.cafeImage.entity.CafeImage;
import com.cupfeedeal.domain.cafeImage.repository.CafeImageRepository;
import com.cupfeedeal.domain.cafeImage.service.CafeImageService;
import com.cupfeedeal.global.exception.ApplicationException;
import com.cupfeedeal.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    private final CafeImageService cafeImageService;
    private final CafeImageRepository cafeImageRepository;

    public Cafe findCafeById(Long id) {
        return cafeRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_CAFE));
    }

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
        final List<Cafe> cafeList = cafeRepository.findTop7ByIsRecommendedIsTrueOrderByCreatedAtDesc();
        return cafeList.stream()
                .map(CafeRecommendationListResponseDto::from)
                .toList();
    }

    /*
    새로 오픈한 cafe 조회
     */
    public List<CafeNewOpenListResponseDto> getNewOpenCafes() {
        final List<Cafe> cafeList = cafeRepository.findTop7ByIsNewOpenIsTrueOrderByCreatedAtDesc();
        return cafeList.stream()
                .map(CafeNewOpenListResponseDto::from)
                .toList();
    }

    /*
    cafe 상세 정보 조회
     */
    public CafeInfoResponseDto getCafeInfo(Long id) {
        Cafe cafe = findCafeById(id);
        List<CafeImage> cafeImages = cafeImageRepository.findAllByCafeId(cafe.getId());
        List<CafeImageResponseDto> cafeImageResponseDtoList = cafeImages.stream()
                .map(CafeImageResponseDto::from)
                .toList();

        // user access token 반영하여 코드 수정
        Boolean is_like = false;
        Boolean is_subscribed = false;

        return CafeInfoResponseDto.from(cafe, cafeImageResponseDtoList, is_like, is_subscribed);
    }

    /*
    cafe 검색 결과 리스트 조회
     */
    public List<CafeListResponseDto> getCafeList(final String name) {
        final List<Cafe> cafeList = cafeRepository.findByNameContaining(name);
        List<CafeListResponseDto> cafeListResponseDtoList = new ArrayList<>();

        cafeList.forEach(cafe -> {
            CafeImage image = cafeImageRepository.findByCafe(cafe);
            cafeListResponseDtoList.add(CafeListResponseDto.from(cafe, image, false));
        });

        return cafeListResponseDtoList;
    }
}
