package com.abb.cbar.service;

import com.abb.cbar.entity.ExchangeRate;
import com.abb.cbar.enums.SupportedCurrency;
import com.abb.cbar.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ConversionService {
    
    private static final MathContext MC = new MathContext(10, RoundingMode.HALF_UP);
    
    private final ExchangeRateService exchangeRateService;
    
    public BigDecimal convert (LocalDate date, String fromCurrency, String toCurrency, BigDecimal amount) {
        fromCurrency = fromCurrency.toUpperCase();
        toCurrency = toCurrency.toUpperCase();
        validateCurrency(fromCurrency);
        validateCurrency(toCurrency);
        
        if (fromCurrency.equals(toCurrency)) return amount.setScale(6, RoundingMode.HALF_UP);
        
        BigDecimal aznAmount = toAzn(date, fromCurrency, amount);
        return fromAzn(date, toCurrency, aznAmount);
    }
    
    private BigDecimal toAzn (LocalDate date, String currency, BigDecimal amount) {
        if (currency.equals("AZN")) return amount;
        ExchangeRate rate = exchangeRateService.getRate(date, currency);
        return amount.multiply(rate.getRateToAzn(), MC)
                .divide(BigDecimal.valueOf(rate.getNominal()), MC);
    }
    
    private BigDecimal fromAzn (LocalDate date, String currency, BigDecimal aznAmount) {
        if (currency.equals("AZN")) return aznAmount.setScale(6, RoundingMode.HALF_UP);
        ExchangeRate rate = exchangeRateService.getRate(date, currency);
        return aznAmount.multiply(BigDecimal.valueOf(rate.getNominal()), MC)
                .divide(rate.getRateToAzn(), 6, RoundingMode.HALF_UP);
    }
    
    private void validateCurrency (String currency) {
        if (!SupportedCurrency.isSupported(currency)) {
            throw ApiException.badRequest("Unsupported currency: " + currency);
        }
    }
}
