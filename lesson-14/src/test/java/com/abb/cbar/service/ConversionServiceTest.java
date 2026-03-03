package com.abb.cbar.service;

import com.abb.cbar.entity.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversionServiceTest {
    
    private static final LocalDate DATE = LocalDate.of(2025, 12, 5);
    @Mock
    private ExchangeRateService exchangeRateService;
    private ConversionService conversionService;
    
    @BeforeEach
    void setUp () {
        conversionService = new ConversionService(exchangeRateService);
    }
    
    @Test
    void convert_usdToAzn () {
        when(exchangeRateService.getRate(DATE, "USD"))
                .thenReturn(new ExchangeRate(DATE, "USD", 1, new BigDecimal("1.7000")));
        
        BigDecimal result = conversionService.convert(DATE, "USD", "AZN", new BigDecimal("100"));
        
        assertThat(result).isEqualByComparingTo(new BigDecimal("170.000000"));
    }
    
    @Test
    void convert_aznToEur () {
        when(exchangeRateService.getRate(DATE, "EUR"))
                .thenReturn(new ExchangeRate(DATE, "EUR", 1, new BigDecimal("1.7935")));
        
        BigDecimal result = conversionService.convert(DATE, "AZN", "EUR", new BigDecimal("100"));
        
        assertThat(result).isEqualByComparingTo(new BigDecimal("55.756900"));
    }
    
    @Test
    void convert_rubToUsd_withNominal100 () {
        when(exchangeRateService.getRate(DATE, "RUB"))
                .thenReturn(new ExchangeRate(DATE, "RUB", 100, new BigDecimal("1.6064")));
        when(exchangeRateService.getRate(DATE, "USD"))
                .thenReturn(new ExchangeRate(DATE, "USD", 1, new BigDecimal("1.7000")));
        
        BigDecimal result = conversionService.convert(DATE, "RUB", "USD", new BigDecimal("10000"));
        
        assertThat(result).isEqualByComparingTo(new BigDecimal("94.494118"));
    }
    
    @Test
    void convert_sameCurrency_returnsAmount () {
        BigDecimal result = conversionService.convert(DATE, "USD", "USD", new BigDecimal("50"));
        
        assertThat(result).isEqualByComparingTo(new BigDecimal("50.000000"));
    }
    
    @Test
    void convert_aznToAzn_returnsAmount () {
        BigDecimal result = conversionService.convert(DATE, "AZN", "AZN", new BigDecimal("123.45"));
        
        assertThat(result).isEqualByComparingTo(new BigDecimal("123.450000"));
    }
}
