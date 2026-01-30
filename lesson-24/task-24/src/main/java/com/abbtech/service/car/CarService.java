package com.abbtech.service.car;

import com.abbtech.dto.car.ReqCarDto;
import com.abbtech.dto.car.RespCarDto;

import java.util.List;

public interface CarService {

    List<RespCarDto> getCars();

    RespCarDto getCarById(int id);

    void addCar(ReqCarDto carDto);

    void deleteCarById(int id);

    void updateCar(int id, ReqCarDto carDto);
}
