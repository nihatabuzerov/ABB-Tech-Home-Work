package com.abbtech.dto.car_model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReqCarModelDto(
        @NotNull(message = "required")
        Integer carBrandId,

        @NotBlank(message = "required")
        @Size(max = 255, message = "max 255")
        String name,

        @NotBlank(message = "required")
        @Size(max = 255, message = "max 255")
        String category,

        Integer yearFrom,

        Integer yearTo
) {
}
