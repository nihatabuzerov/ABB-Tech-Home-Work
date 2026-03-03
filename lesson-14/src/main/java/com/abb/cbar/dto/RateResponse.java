package com.abb.cbar.dto;

import java.math.BigDecimal;
import java.util.List;

public record RateResponse(String date, List<CurrencyRate> rates) {
    
    public record CurrencyRate(
            String currency,
            int nominal,
            BigDecimal rate
    ) { }
}
