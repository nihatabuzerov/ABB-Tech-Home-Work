package com.abb.cbar.dto;

import java.math.BigDecimal;

public record ConversionResponse(
        String from,
        String to,
        BigDecimal amount,
        BigDecimal result,
        String date
) { }
