package com.cupfeedeal.domain.cafeImage.service;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafeImage.dto.request.CafeImageCreateRequestDto;
import com.cupfeedeal.domain.cafeImage.entity.CafeImage;
import com.cupfeedeal.domain.cafeImage.repository.CafeImageRepository;
import com.cupfeedeal.global.exception.ApplicationException;
import com.cupfeedeal.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeImageService {
    private final CafeImageRepository cafeImageRepository;

    /*
    cafe image 생성
     */
    @Transactional
    public void createCafeImage(CafeImageCreateRequestDto requestDto) {
        final CafeImage cafeImage = requestDto.toEntity();
        cafeImageRepository.save(cafeImage);
    }

    public CafeImage findMainCafeImage(Cafe cafe){
        return cafeImageRepository.findByCafeAndIsMainImageIsTrue(cafe)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_CAFE));
    }
}
