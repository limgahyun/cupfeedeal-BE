package com.cupfeedeal.domain.cafeSubscriptionType.service;

import com.cupfeedeal.domain.UserSubscription.sevice.UserSubscriptionService;
import com.cupfeedeal.domain.cafe.service.CafeService;
import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import com.cupfeedeal.domain.cafeSubscriptionType.repository.CafeSubscriptionTypeRepository;
import com.cupfeedeal.global.exception.ApplicationException;
import com.cupfeedeal.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CafeSubscriptionTypeService {
    private final CafeSubscriptionTypeRepository cafeSubscriptionTypeRepository;

    public CafeSubscriptionType findCafeSubscriptionTypeById(Long cafeSubscriptionTypeId) {
        return cafeSubscriptionTypeRepository.findById(cafeSubscriptionTypeId)
                .orElseThrow(() -> new ApplicationException(ExceptionCode.NOT_FOUND_CAFE_SUBSCRIPTION_TYPE));
    }

    public List<Integer> calculateSavedCups(CafeSubscriptionType cafeSubscriptionType) {
        Integer price = cafeSubscriptionType.getPrice();
        Integer drinkPrice = cafeSubscriptionType.getOriginal_drink_price();
        Integer period = cafeSubscriptionType.getPeriod();

        // 손익분기점
        Integer breakEvenPoint = price / drinkPrice;

        // 반 컵을 아끼게 되는 날 수
        Double daysPerHalfCup = (period - breakEvenPoint) / 6.0;

        // 기준 날짜 계산 (0.5컵, 1컵, 1.5컵, 2컵, 2.5컵, 3컵)
        List<Integer> breakDays = new ArrayList<>();

        // k 값을 1부터 6까지 순회하며 breakEventPoint + (daysPerHalfCup * k) 계산
        for (int k = 1; k <= 6; k++) {
            // daysPerHalfCup * k 값을 소숫점 내림
            int days = (int) Math.floor(daysPerHalfCup * k);

            int breakDay = breakEvenPoint + days;
            breakDays.add(breakDay);
        }

        return breakDays;
    }

    @Transactional
    public void setSubscriptionBreakDays(Long cafeSubscriptionTypeId) {
        CafeSubscriptionType cafeSubscriptionType = findCafeSubscriptionTypeById(cafeSubscriptionTypeId);

        // breakDays 값이 비어있는 경우
        log.info("start");
        List<Integer> calculatedBreakDays = calculateSavedCups(cafeSubscriptionType);
        log.info("calculatedBreakDays : {}", calculatedBreakDays);
        cafeSubscriptionType.setBreakDays(new ArrayList<>(calculatedBreakDays));

        cafeSubscriptionTypeRepository.save(cafeSubscriptionType);
    }
}
