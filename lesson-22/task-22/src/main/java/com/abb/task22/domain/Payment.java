package com.abb.task22.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Payment {
    
    private UUID id;
    
    private UUID userId;
    
    private BigDecimal amount;
    
    private PaymentStatus status;
    
    private LocalDateTime createdAt;
}