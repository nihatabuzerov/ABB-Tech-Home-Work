package com.abb.cbar.controller;

import com.abb.cbar.dto.ConversionRequest;
import com.abb.cbar.dto.ConversionResponse;
import com.abb.cbar.service.ConversionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConversionController {
    
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final ConversionService conversionService;
    
    @PostMapping("/convert")
    public ResponseEntity<ConversionResponse> convert (@Valid @RequestBody ConversionRequest request) {
        
        LocalDate date = LocalDate.parse(request.date(), DATE_FORMAT);
        BigDecimal result = conversionService.convert(date, request.from(), request.to(), request.amount());
        
        return ResponseEntity.ok(new ConversionResponse(
                request.from().toUpperCase(), request.to().toUpperCase(),
                request.amount(), result, request.date()));
    }
}
