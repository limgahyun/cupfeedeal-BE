package com.cupfeedeal.domain.User.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Builder
@Getter
public class PaymentHistoryResponseDto {
    public String cafeName;
    public Integer subscriptionPrice;
    public Integer subscriptionPeriod;
    public LocalDateTime subscriptionStart;
    public LocalDateTime subscriptionDeadline;
}
