package com.abb.cbar.repository;

import com.abb.cbar.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    
    Optional<ExchangeRate> findByRateDateAndCurrencyCode (LocalDate rateDate, String currencyCode);
    
    List<ExchangeRate> findByRateDate (LocalDate rateDate);
}
