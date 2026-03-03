package com.abb.cbar.controller;

import com.abb.cbar.dto.RateResponse;
import com.abb.cbar.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/rates")
@RequiredArgsConstructor
public class RateController {
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final ExchangeRateService exchangeRateService;
    
    @GetMapping
    public ResponseEntity<RateResponse> getRates (@RequestParam String date) {
        var localDate = LocalDate.parse(date, DATE_FORMAT);
       
        var rates = exchangeRateService
                .getRatesForDate(localDate)
                .stream()
                .map(r -> new RateResponse.CurrencyRate(r.getCurrencyCode(), r.getNominal(), r.getRateToAzn()))
                .toList();
        return ResponseEntity.ok(new RateResponse(date, rates));
    }
}
