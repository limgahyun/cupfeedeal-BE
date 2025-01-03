package com.cupfeedeal.domain.cafe.service;

import com.cupfeedeal.domain.cafe.dto.request.CafeCreateRequestDto;
import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafe.repository.CafeRepository;
import com.cupfeedeal.domain.cafeImage.dto.request.CafeImageCreateRequestDto;
import com.cupfeedeal.domain.cafeImage.entity.CafeImage;
import com.cupfeedeal.domain.cafeImage.repository.CafeImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeService {

    private final CafeRepository cafeRepository;
    private final CafeImageRepository cafeImageRepository;

    /*
    cafe 생성
     */
    @Transactional
    public void createCafe(CafeCreateRequestDto requestDto) {
        // cafe 저장
        final Cafe cafe = requestDto.toEntity();
        cafeRepository.save(cafe);

        // imageRequestDtos를 cafeImage entity로 변환하여 저장
        List<CafeImageCreateRequestDto> imageRequestDtos = requestDto.images();
        List<CafeImage> images = new ArrayList<>();

        imageRequestDtos.forEach(imageRequestDto -> {
            images.add(imageRequestDto.toEntity(cafe));
        });
        cafeImageRepository.saveAll(images);
    }
}
