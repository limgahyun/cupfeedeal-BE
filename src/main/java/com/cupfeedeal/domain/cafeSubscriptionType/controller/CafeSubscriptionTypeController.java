package com.cupfeedeal.domain.cafeSubscriptionType.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cafe", description = "cafe api")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cafe")
public class CafeSubscriptionTypeController {
}
