package com.cupfeedeal.domain.cafeSubscriptionType.service;

import com.cupfeedeal.domain.cafe.entity.Cafe;
import com.cupfeedeal.domain.cafe.repository.CafeRepository;
import com.cupfeedeal.domain.cafe.service.CafeService;
import com.cupfeedeal.domain.cafeSubscriptionType.dto.response.CafeSubscriptionInfoResponseDto;
import com.cupfeedeal.domain.cafeSubscriptionType.dto.response.CafeSubscriptionListResponseDto;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import com.cupfeedeal.domain.cafeSubscriptionType.repository.CafeSubscriptionTypeRepository;
import com.cupfeedeal.global.exception.ApplicationException;
import com.cupfeedeal.global.exception.ExceptionCode;
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

    public CafeSubscriptionType findCafeSubscriptionTypeById(Long cafeSubscriptionTypeId) {
        return cafeSubscriptionTypeRepository.findById(cafeSubscriptionTypeId)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_CAFE_SUBSCRIPTION_TYPE));
    }

    public CafeSubscriptionInfoResponseDto getCafeSubscriptionType(Long cafe_id) {
        Cafe cafe = cafeService.findCafeById(cafe_id);
        List<CafeSubscriptionType> cafeSubscriptionTypeList = cafeSubscriptionTypeRepository.findAllByCafeId(cafe_id);

        // cafeSubscriptionType list를 dto로 변환
        List<CafeSubscriptionListResponseDto> cafeSubscriptionListResponseDtoList = cafeSubscriptionTypeList.stream()
                .map(CafeSubscriptionListResponseDto::from)
                .toList();

        return CafeSubscriptionInfoResponseDto.from(cafe, cafeSubscriptionListResponseDtoList);
    }
}
