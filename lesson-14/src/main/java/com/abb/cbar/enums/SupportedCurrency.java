package com.abb.cbar.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum SupportedCurrency {
    
    AZN("AZN", "Azerbaijan Manat", 1),
    EUR("EUR", "Euro", 1),
    RUB("RUB", "Russian Ruble", 100),
    TRY("TRY", "Turkish Lira", 1),
    USD("USD", "US Dollar", 1);
    
    private static final Map<String, SupportedCurrency> BY_CODE =
            Arrays.stream(values()).collect(Collectors.toMap(SupportedCurrency::getCode, Function.identity()));
    
    private final String code;
    private final String displayName;
    private final int defaultNominal;
    
    SupportedCurrency (
            String code,
            String displayName,
            int defaultNominal
    ) {
        this.code = code;
        this.displayName = displayName;
        this.defaultNominal = defaultNominal;
    }
    
    public static boolean isSupported (String code) {
        return BY_CODE.containsKey(code.toUpperCase());
    }
}
