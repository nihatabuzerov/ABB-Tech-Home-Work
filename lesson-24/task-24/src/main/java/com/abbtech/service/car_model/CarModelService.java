package com.abbtech.service.car_model;

import com.abbtech.dto.car_model.ReqCarModelDto;
import com.abbtech.dto.car_model.RespCarModelDto;

import java.util.List;

public interface CarModelService {

    List<RespCarModelDto> getModels();

    RespCarModelDto getModelById(int id);

    void addModel(ReqCarModelDto modelDto);

    void deleteModelById(int id);

    void updateModel(int id, ReqCarModelDto modelDto);
}
