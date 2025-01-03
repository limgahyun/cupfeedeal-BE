package com.cupfeedeal.domain.cafe.service;

import com.cupfeedeal.domain.cafe.dto.request.CafeCreateRequestDto;
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
}
