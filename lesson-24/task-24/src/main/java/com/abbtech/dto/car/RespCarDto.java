package com.abbtech.dto.car;

import com.abbtech.dto.car_details.CarDetailsDto;

public record RespCarDto(
        Integer id,
        String vin,
        String registrationNumber,
        Integer mileageKm,
        Integer productionYear,
        Integer carModelId,
        String carModelName,
        Integer carBrandId,
        String carBrandName,
        CarDetailsDto carDetails
) {
}
