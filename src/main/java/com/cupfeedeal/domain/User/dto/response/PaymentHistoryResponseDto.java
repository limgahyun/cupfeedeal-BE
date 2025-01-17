package com.cupfeedeal.domain.User.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Getter
public class PaymentHistoryResponseDto {
    private String cafeName;
    private Integer subscriptionPrice;
    private Integer subscriptionPeriod;
    private LocalDateTime subscriptionStart;
    private LocalDateTime subscriptionDeadline;
}
