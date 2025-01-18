package com.cupfeedeal.domain.cafeSubscriptionType.service;

import com.cupfeedeal.domain.cafe.repository.CafeRepository;
import com.cupfeedeal.domain.cafe.service.CafeService;
import com.cupfeedeal.domain.cafeSubscriptionType.dto.response.CafeSubscriptionInfoResponseDto;
import com.cupfeedeal.domain.cafeSubscriptionType.dto.response.CafeSubscriptionListResponseDto;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import com.cupfeedeal.domain.cafeSubscriptionType.repository.CafeSubscriptionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeSubscriptionTypeService {
    private final CafeService cafeService;
    private final CafeSubscriptionTypeRepository cafeSubscriptionTypeRepository;
    private final CafeRepository cafeRepository;

    public CafeSubscriptionInfoResponseDto getCafeSubscriptionType(Long cafe_id) {
        String cafeName = cafeRepository.findNameByCafeId(cafe_id);

        List<CafeSubscriptionType> cafeSubscriptionTypeList = cafeSubscriptionTypeRepository.findAllByCafeId(cafe_id);

        // cafeSubscriptionType list를 dto로 변환
        List<CafeSubscriptionListResponseDto> cafeSubscriptionListResponseDtoList = cafeSubscriptionTypeList.stream()
                .map(CafeSubscriptionListResponseDto::from)
                .toList();

        return CafeSubscriptionInfoResponseDto.from(cafe_id, cafeName, cafeSubscriptionListResponseDtoList);
    }
}
