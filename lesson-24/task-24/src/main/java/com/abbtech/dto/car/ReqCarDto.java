package com.abbtech.dto.car;

import com.abbtech.dto.car_details.ReqCarDetailsDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ReqCarDto(
        @NotNull(message = "required")
        Integer carModelId,

        @NotBlank(message = "required")
        @Size(max = 20, message = "vin can be max 20")
        String vin,

        @NotBlank(message = "required")
        @Size(max = 10, message = "registration number can be max 10")
        String registrationNumber,

        @NotNull(message = "required")
        @PositiveOrZero(message = "mileageKm must be >= 0")
        Integer mileageKm,

        Integer productionYear,

        @Valid
        ReqCarDetailsDto carDetails
) {
}
