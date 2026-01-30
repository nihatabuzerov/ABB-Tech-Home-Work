package com.abb.task22.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserPaymentDto {
    private UUID paymentId;
    private BigDecimal amount;
    private String status;
    private LocalDateTime createdAt;
}
