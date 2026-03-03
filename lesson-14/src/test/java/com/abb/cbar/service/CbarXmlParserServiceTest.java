package com.abb.cbar.service;

import com.abb.cbar.entity.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CbarXmlParserServiceTest {
    
    private CbarXmlParserService parserService;
    
    @BeforeEach
    void setUp () {
        parserService = new CbarXmlParserService(RestClient.builder().build());
    }
    
    @Test
    void parseXml_shouldExtractSupportedCurrencies () throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/test-rates.xml"));
        LocalDate date = LocalDate.of(2025, 12, 5);
        
        List<ExchangeRate> rates = parserService.parseXml(xml, date);
        
        assertThat(rates).hasSize(4);
        assertThat(rates).extracting(ExchangeRate::getCurrencyCode)
                .containsExactlyInAnyOrder("USD", "EUR", "RUB", "TRY");
    }
    
    @Test
    void parseXml_shouldExtractCorrectUsdRate () throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/test-rates.xml"));
        LocalDate date = LocalDate.of(2025, 12, 5);
        
        List<ExchangeRate> rates = parserService.parseXml(xml, date);
        
        ExchangeRate usd = rates.stream()
                .filter(r -> r.getCurrencyCode().equals("USD"))
                .findFirst().orElseThrow();
        assertThat(usd.getNominal()).isEqualTo(1);
        assertThat(usd.getRateToAzn()).isEqualByComparingTo(new BigDecimal("1.7000"));
        assertThat(usd.getRateDate()).isEqualTo(date);
    }
    
    @Test
    void parseXml_shouldExtractCorrectRubRate_withNominal100 () throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/test-rates.xml"));
        LocalDate date = LocalDate.of(2025, 12, 5);
        
        List<ExchangeRate> rates = parserService.parseXml(xml, date);
        
        ExchangeRate rub = rates.stream()
                .filter(r -> r.getCurrencyCode().equals("RUB"))
                .findFirst().orElseThrow();
        assertThat(rub.getNominal()).isEqualTo(100);
        assertThat(rub.getRateToAzn()).isEqualByComparingTo(new BigDecimal("1.6064"));
    }
    
    @Test
    void parseXml_shouldFilterOutUnsupportedCurrencies () throws Exception {
        String xml = Files.readString(Path.of("src/test/resources/test-rates.xml"));
        LocalDate date = LocalDate.of(2025, 12, 5);
        
        List<ExchangeRate> rates = parserService.parseXml(xml, date);
        
        assertThat(rates).extracting(ExchangeRate::getCurrencyCode)
                .doesNotContain("GBP", "JPY");
    }
}
