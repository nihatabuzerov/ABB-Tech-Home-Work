package com.abb.task22.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full max most 100 characters")
    private String fullName;

    @NotNull(message = "Balance is required")
    @PositiveOrZero(message = "Balance cannot be below 0")
    private BigDecimal balance;
}
