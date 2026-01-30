package com.abbtech.dto.car_model;

public record RespCarModelDto(
        Integer id,
        String name,
        String category,
        Integer yearFrom,
        Integer yearTo,
        Integer carBrandId,
        String carBrandName
) {
}
