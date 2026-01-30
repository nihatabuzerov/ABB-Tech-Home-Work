package com.abb.task22.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PaymentResponse {
    private UUID paymentId;
    private String status;
    private BigDecimal balance;
}
