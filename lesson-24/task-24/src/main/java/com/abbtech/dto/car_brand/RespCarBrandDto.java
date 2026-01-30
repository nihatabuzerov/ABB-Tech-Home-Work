package com.abbtech.dto.car_brand;

public record RespCarBrandDto(
        Integer id,
        String name,
        String country,
        Integer foundedYear
) {
}
