package com.cupfeedeal.domain.cafeImage.service;

import com.cupfeedeal.domain.cafeImage.dto.request.CafeImageCreateRequestDto;
import com.cupfeedeal.domain.cafeImage.entity.CafeImage;
import com.cupfeedeal.domain.cafeImage.repository.CafeImageRepository;
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
}
