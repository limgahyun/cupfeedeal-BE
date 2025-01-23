package com.cupfeedeal.domain.cafeSubscriptionType.controller;

import com.cupfeedeal.domain.cafeSubscriptionType.entity.CafeSubscriptionType;
import com.cupfeedeal.domain.cafeSubscriptionType.service.CafeSubscriptionTypeService;
import com.cupfeedeal.global.common.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cafe Subscription Type", description = "cafe subscription type api")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cafeSubscriptionType")
public class CafeSubscriptionTypeController {
    private final CafeSubscriptionTypeService cafeSubscriptionTypeService;

    @Operation(summary = "db 쌓기 용 api")
    @GetMapping("/{cafeSubscriptionTypeId}")
    public CommonResponse<Void> setCafeSubscriptionBreakDays(@PathVariable Long cafeSubscriptionTypeId){
        cafeSubscriptionTypeService.setSubscriptionBreakDays(cafeSubscriptionTypeId);

        return new CommonResponse<>("set break days successfully");
    }
}
