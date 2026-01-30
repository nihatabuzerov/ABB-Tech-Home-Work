package com.abbtech.mapper;

import com.abbtech.dto.car_details.CarDetailsDto;
import com.abbtech.model.CarDetails;

public class CarDetailsMapper {

    private CarDetailsMapper() {}

    public static CarDetailsDto toDto(CarDetails details) {
        if (details == null) return null;

        return new CarDetailsDto(
                details.getId(),
                details.getEngineNumber(),
                details.getRegistrationCode(),
                details.getColor(),
                details.getInsuranceNumber(),
                details.getFuelType(),
                details.getEngineCapacity()
        );
    }
}
