package com.cupfeedeal.domain.Cupcat.service;

import com.cupfeedeal.domain.Cupcat.enumerate.CupcatTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CupcatTypeUtilService {
    private static final Random RANDOM = new Random();

    public CupcatTypeEnum getRandomCupcatType() {
        CupcatTypeEnum[] values = CupcatTypeEnum.values();
        int randomIndex = RANDOM.nextInt(values.length);
        return values[randomIndex];
    }
}
