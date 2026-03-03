package com.abb.cbar.controller;

import com.abb.cbar.entity.ExchangeRate;
import com.abb.cbar.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RateControllerTest {
    
    @Mock
    private ExchangeRateService exchangeRateService;
    
    private RateController rateController;
    
    @BeforeEach
    void setUp () {
        rateController = new RateController(exchangeRateService);
    }
    
    @Test
    void getRates_returnsAllRates () {
        var date = LocalDate.of(2025, 12, 5);
        when(exchangeRateService.getRatesForDate(date))
                .thenReturn(List.of(
                        new ExchangeRate(date, "USD", 1, new BigDecimal("1.7000")),
                        new ExchangeRate(date, "EUR", 1, new BigDecimal("1.7935"))
                ));
        
        var response = rateController.getRates("05.12.2025");
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assert response.getBody() != null;
        assertThat(response.getBody().date()).isEqualTo("05.12.2025");
        assertThat(response.getBody().rates()).hasSize(2);
    }
}
