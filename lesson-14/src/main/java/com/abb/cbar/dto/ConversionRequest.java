package com.abb.cbar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ConversionRequest(
        @NotBlank(message = "Date is required (dd.MM.yyyy)")
        String date,
        
        @NotBlank(message = "Source currency is required")
        String from,
        
        @NotBlank(message = "Target currency is required")
        String to,
        
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount
) { }
