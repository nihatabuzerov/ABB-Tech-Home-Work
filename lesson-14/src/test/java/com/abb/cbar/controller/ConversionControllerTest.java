package com.abb.cbar.controller;

import com.abb.cbar.dto.ConversionRequest;
import com.abb.cbar.dto.ConversionResponse;
import com.abb.cbar.service.ConversionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConversionControllerTest {
    
    @Mock
    private ConversionService conversionService;
    
    private ConversionController conversionController;
    
    @BeforeEach
    void setUp () {
        conversionController = new ConversionController(conversionService);
    }
    
    @Test
    void convert_success () {
        when(conversionService.convert(
                eq(LocalDate.of(2025, 12, 5)),
                eq("USD"), eq("AZN"),
                any(BigDecimal.class)))
                .thenReturn(new BigDecimal("170.000000"));
        
        ResponseEntity<ConversionResponse> response = conversionController.convert(
                new ConversionRequest("05.12.2025", "USD", "AZN", new BigDecimal("100")));
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assert response.getBody() != null;
        assertThat(response.getBody().from()).isEqualTo("USD");
        assertThat(response.getBody().to()).isEqualTo("AZN");
        assertThat(response.getBody().result()).isEqualByComparingTo(new BigDecimal("170.000000"));
    }
    
    @Test
    void convert_returnsCorrectDate () {
        when(conversionService.convert(any(), any(), any(), any()))
                .thenReturn(new BigDecimal("100"));
        
        ResponseEntity<ConversionResponse> response = conversionController.convert(
                new ConversionRequest("05.12.2025", "AZN", "AZN", new BigDecimal("100")));
        
        assert response.getBody() != null;
        assertThat(response.getBody().date()).isEqualTo("05.12.2025");
    }
}
