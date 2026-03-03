package com.abb.cbar.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "exchange_rates",
        uniqueConstraints = @UniqueConstraint(columnNames = { "rate_date", "currency_code" }))
@Getter
@Setter
@NoArgsConstructor
public class ExchangeRate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rate_date", nullable = false)
    private LocalDate rateDate;
    
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;
    
    @Column(nullable = false)
    private Integer nominal;
    
    @Column(name = "rate_to_azn", nullable = false, precision = 18, scale = 6)
    private BigDecimal rateToAzn;
    
    @Column(name = "fetched_at", nullable = false, updatable = false)
    private LocalDateTime fetchedAt = LocalDateTime.now();
    
    public ExchangeRate (LocalDate rateDate, String currencyCode, Integer nominal, BigDecimal rateToAzn) {
        this.rateDate = rateDate;
        this.currencyCode = currencyCode;
        this.nominal = nominal;
        this.rateToAzn = rateToAzn;
    }
}
