package com.abbtech.service.car_model;

import com.abbtech.dto.car_model.ReqCarModelDto;
import com.abbtech.dto.car_model.RespCarModelDto;
import com.abbtech.exception.car.CarErrorEnum;
import com.abbtech.exception.car.CarException;
import com.abbtech.model.CarBrand;
import com.abbtech.model.CarModel;
import com.abbtech.repository.BrandRepository;
import com.abbtech.repository.CarModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarModelServiceImpl implements CarModelService {

    private final CarModelRepository modelRepository;
    private final BrandRepository brandRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RespCarModelDto> getModels() {
        return modelRepository
                .findAll()
                .stream()
                .map(CarModelServiceImpl::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RespCarModelDto getModelById(int id) {
        CarModel carModel = modelRepository
                .findById(id)
                .orElseThrow(() -> new CarException(CarErrorEnum.MODEL_NOT_FOUND));
        return toDto(carModel);
    }

    @Override
    @Transactional
    public void addModel(ReqCarModelDto modelDto) {
        CarBrand carBrand = brandRepository
                .findById(modelDto.carBrandId())
                .orElseThrow(() -> new CarException(CarErrorEnum.BRAND_NOT_FOUND));

        CarModel carModel = toEntity(modelDto, carBrand);
      
        modelRepository.save(carModel);
    }

    @Override
    @Transactional
    public void deleteModelById(int id) {
        CarModel carModel = modelRepository
                .findById(id)
                .orElseThrow(() -> new CarException(CarErrorEnum.MODEL_NOT_FOUND));
        modelRepository.delete(carModel);
    }

    @Override
    @Transactional
    public void updateModel(int id, ReqCarModelDto modelDto) {
        CarModel carModel = modelRepository
                .findById(id)
                .orElseThrow(() -> new CarException(CarErrorEnum.MODEL_NOT_FOUND));

        if (modelDto.carBrandId() != null && !modelDto.carBrandId().equals(carModel.getCarBrand().getId())) {
            CarBrand carBrand = brandRepository
                    .findById(modelDto.carBrandId())
                    .orElseThrow(() -> new CarException(CarErrorEnum.BRAND_NOT_FOUND));
            carModel.setCarBrand(carBrand);
        }

        carModel.setName(modelDto.name());
        carModel.setCategory(modelDto.category());
        carModel.setYearFrom(modelDto.yearFrom());
        carModel.setYearTo(modelDto.yearTo());
        modelRepository.save(carModel);
    }

    private static RespCarModelDto toDto(CarModel carModel) {
        return new RespCarModelDto(
                carModel.getId(),
                carModel.getName(),
                carModel.getCategory(),
                carModel.getYearFrom(),
                carModel.getYearTo(),
                carModel.getCarBrand().getId(),
                carModel.getCarBrand().getName()
        );
    }

    private static CarModel toEntity(ReqCarModelDto dto, CarBrand carBrand) {
        return CarModel.builder()
                .carBrand(carBrand)
                .name(dto.name())
                .category(dto.category())
                .yearFrom(dto.yearFrom())
                .yearTo(dto.yearTo())
                .build();
    }
}
