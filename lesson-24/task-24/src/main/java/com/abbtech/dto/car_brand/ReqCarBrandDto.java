package com.abbtech.dto.car_brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReqCarBrandDto(
        @NotBlank(message = "required")
        @Size(max = 255, message = "max 255")
        String name,

        @Size(max = 255, message = "max 255")
        String country,

        Integer foundedYear
) {
}
