package com.abb.cbar.service;

import com.abb.cbar.entity.ExchangeRate;
import com.abb.cbar.enums.SupportedCurrency;
import com.abb.cbar.exception.ApiException;
import com.abb.cbar.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    
    private final ExchangeRateRepository rateRepository;
    private final CbarXmlParserService cbarXmlParserService;
    
    public ExchangeRate getRate (LocalDate date, String currencyCode) {
        validateCurrency(currencyCode);
        return rateRepository.findByRateDateAndCurrencyCode(date, currencyCode.toUpperCase())
                .orElseGet(() -> {
                    fetchAndCacheRates(date);
                    return rateRepository.findByRateDateAndCurrencyCode(date, currencyCode.toUpperCase())
                            .orElseThrow(() -> ApiException.notFound(
                                    "Rate not found for " + currencyCode + " on " + date));
                });
    }
    
    public List<ExchangeRate> getRatesForDate (LocalDate date) {
        List<ExchangeRate> cached = rateRepository.findByRateDate(date);
        if (cached.size() >= SupportedCurrency.values().length - 1) return cached;
        fetchAndCacheRates(date);
        return rateRepository.findByRateDate(date);
    }
    
    private void fetchAndCacheRates (LocalDate date) {
        cbarXmlParserService.fetchRates(date).forEach(rate -> {
            if (rateRepository.findByRateDateAndCurrencyCode(rate.getRateDate(), rate.getCurrencyCode()).isEmpty()) {
                rateRepository.save(rate);
            }
        });
    }
    
    private void validateCurrency (String currencyCode) {
        if (!SupportedCurrency.isSupported(currencyCode)) {
            throw ApiException.badRequest("Unsupported currency: " + currencyCode);
        }
    }
}
