package com.cupfeedeal.domain.cafe.service;

import com.cupfeedeal.domain.User.entity.CustomUserdetails;
import com.cupfeedeal.domain.User.entity.User;
import com.cupfeedeal.domain.UserCafeLike.repository.UserCafeLikeRepository;
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
    private final UserCafeLikeRepository userCafeLikeRepository;

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

        List<CafeRecommendationListResponseDto> cafeRecommendationListResponseDtoList = new ArrayList<>();
        cafeList.forEach(cafe -> {
            CafeImage image = cafeImageService.findMainCafeImage(cafe);
            cafeRecommendationListResponseDtoList.add(CafeRecommendationListResponseDto.from(cafe, image));
        });

        return cafeRecommendationListResponseDtoList;
    }

    /*
    새로 오픈한 cafe 조회
     */
    public List<CafeNewOpenListResponseDto> getNewOpenCafes() {
        final List<Cafe> cafeList = cafeRepository.findTop7ByIsNewOpenIsTrueOrderByCreatedAtDesc();

        List<CafeNewOpenListResponseDto> cafeNewOpenListResponseDtoList = new ArrayList<>();
        cafeList.forEach(cafe -> {
            CafeImage image = cafeImageService.findMainCafeImage(cafe);
            cafeNewOpenListResponseDtoList.add(CafeNewOpenListResponseDto.from(cafe, image));
        });

        return cafeNewOpenListResponseDtoList;
    }

    /*
    cafe 상세 정보 조회
     */
    public CafeInfoResponseDto getCafeInfo(Long id, CustomUserdetails customUserdetails) {
        User user = (customUserdetails == null) ? null : customUserdetails.getUser();
        Cafe cafe = findCafeById(id);

        List<CafeImage> cafeImages = cafeImageRepository.findAllByCafeId(cafe.getId());
        List<CafeImageResponseDto> cafeImageResponseDtoList = cafeImages.stream()
                .map(CafeImageResponseDto::from)
                .toList();

        // 카페 저장 여부 반환
        Boolean is_like = (user != null) && userCafeLikeRepository.findByUserAndCafe(user, cafe).isPresent();

        // 카페 구독 여부 반환
        Boolean is_subscribed = false;

        return CafeInfoResponseDto.from(cafe, cafeImageResponseDtoList, is_like, is_subscribed);
    }

    /*
    Cafe 검색 결과 리스트 조회
    */
    public List<CafeListResponseDto> getCafeList(final String name, final CustomUserdetails customUserdetails, final Boolean isLike) {
        List<Cafe> cafeList;

        if (customUserdetails == null) {
            // 인증되지 않은 사용자 로직
            cafeList = cafeRepository.findByNameContaining(name);
            return mapToCafeListResponseDtos(cafeList, null);
        }

        // 인증된 사용자 로직
        User user = customUserdetails.getUser();
        if (isLike) {
            // 좋아요 한 카페만 조회
            cafeList = cafeRepository.findByNameContainingAndUserLike(name, user);
        } else {
            // 전체 카페 조회
            cafeList = cafeRepository.findByNameContaining(name);
        }
        return mapToCafeListResponseDtos(cafeList, user);
    }

    /*
        공통 로직: Cafe 리스트를 CafeListResponseDto로 변환
    */
    private List<CafeListResponseDto> mapToCafeListResponseDtos(List<Cafe> cafeList, User user) {
        List<CafeListResponseDto> cafeListResponseDtoList = new ArrayList<>();

        cafeList.forEach(cafe -> {
            // Cafe 이미지 조회
            CafeImage image = cafeImageRepository.findByCafeAndIsMainImageIsTrue(cafe).orElse(null);

            // 저장 여부 반환 (인증된 사용자일 경우에만 확인)
            boolean isLike = user != null && userCafeLikeRepository.findByUserAndCafe(user, cafe).isPresent();

            cafeListResponseDtoList.add(CafeListResponseDto.from(cafe, image, isLike));
        });

        return cafeListResponseDtoList;
    }
}
