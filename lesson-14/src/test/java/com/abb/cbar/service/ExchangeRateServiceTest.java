package com.abb.cbar.service;

import com.abb.cbar.entity.ExchangeRate;
import com.abb.cbar.exception.ApiException;
import com.abb.cbar.repository.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {
    
    private static final LocalDate DATE = LocalDate.of(2025, 12, 5);
    @Mock
    private ExchangeRateRepository rateRepository;
    @Mock
    private CbarXmlParserService cbarXmlParserService;
    private ExchangeRateService exchangeRateService;
    
    @BeforeEach
    void setUp () {
        exchangeRateService = new ExchangeRateService(rateRepository, cbarXmlParserService);
    }
    
    @Test
    void getRate_cacheHit_doesNotFetchFromCbar () {
        var cached = new ExchangeRate(DATE, "USD", 1, new BigDecimal("1.7000"));
        when(rateRepository.findByRateDateAndCurrencyCode(DATE, "USD"))
                .thenReturn(Optional.of(cached));
        
        var result = exchangeRateService.getRate(DATE, "USD");
        
        assertThat(result.getCurrencyCode()).isEqualTo("USD");
        verify(cbarXmlParserService, never()).fetchRates(any());
    }
    
    @Test
    void getRate_cacheMiss_fetchesFromCbar () {
        when(rateRepository.findByRateDateAndCurrencyCode(DATE, "USD"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(new ExchangeRate(DATE, "USD", 1, new BigDecimal("1.7000"))));
        
        when(cbarXmlParserService.fetchRates(DATE))
                .thenReturn(List.of(
                        new ExchangeRate(DATE, "USD", 1, new BigDecimal("1.7000")),
                        new ExchangeRate(DATE, "EUR", 1, new BigDecimal("1.7935"))
                ));
        
        var result = exchangeRateService.getRate(DATE, "USD");
        
        assertThat(result.getCurrencyCode()).isEqualTo("USD");
        verify(cbarXmlParserService).fetchRates(DATE);
        verify(rateRepository, atLeastOnce()).save(any());
    }
    
    @Test
    void getRate_cacheMiss_notFoundAfterFetch_throwsException () {
        when(rateRepository.findByRateDateAndCurrencyCode(DATE, "EUR"))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.empty());
        when(cbarXmlParserService.fetchRates(DATE)).thenReturn(List.of());
        
        assertThatThrownBy(() -> exchangeRateService.getRate(DATE, "EUR"))
                .isInstanceOf(ApiException.class);
    }
}
