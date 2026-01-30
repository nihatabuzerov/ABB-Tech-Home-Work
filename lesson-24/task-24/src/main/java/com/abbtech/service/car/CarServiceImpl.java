package com.abbtech.service.car;

import com.abbtech.dto.car_details.ReqCarDetailsDto;
import com.abbtech.dto.car.ReqCarDto;
import com.abbtech.dto.car.RespCarDto;
import com.abbtech.mapper.CarDetailsMapper;
import com.abbtech.exception.car.CarErrorEnum;
import com.abbtech.exception.car.CarException;
import com.abbtech.model.Car;
import com.abbtech.model.CarDetails;
import com.abbtech.model.CarModel;
import com.abbtech.repository.CarRepository;
import com.abbtech.repository.CarModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarModelRepository modelRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RespCarDto> getCars() {
        return carRepository
                .findAll()
                .stream()
                .map(CarServiceImpl::toDto)
                .toList();
    }

    @Override
    public RespCarDto getCarById(int id) {
        Car car = carRepository
                .findById(id)
                .orElseThrow(() -> new CarException(CarErrorEnum.CAR_NOT_FOUND));
       
        return toDto(car);
    }

    @Override
    @Transactional
    public void addCar(ReqCarDto carDto) {
        CarModel carModel = modelRepository
                .findById(carDto.carModelId())
                .orElseThrow(() -> new CarException(CarErrorEnum.MODEL_NOT_FOUND));

        Car car = toEntity(carDto, carModel);

        if (carDto.carDetails() != null) {
            CarDetails details = toCarDetailsEntity(carDto.carDetails(), car);
            car.setCarDetails(details);
        }

        carRepository.save(car);
    }

    @Override
    @Transactional
    public void deleteCarById(int id) {
        Car car = carRepository
                .findById(id)
                .orElseThrow(() -> new CarException(CarErrorEnum.CAR_NOT_FOUND));
       
        carRepository.delete(car);
    }

    @Override
    @Transactional
    public void updateCar(int id, ReqCarDto carDto) {
        Car car = carRepository
                .findById(id)
                .orElseThrow(() -> new CarException(CarErrorEnum.CAR_NOT_FOUND));

        if (carDto.carModelId() != null && !carDto.carModelId().equals(car.getCarModel().getId())) {
            CarModel carModel = modelRepository
                    .findById(carDto.carModelId())
                    .orElseThrow(() -> new CarException(CarErrorEnum.MODEL_NOT_FOUND));
            car.setCarModel(carModel);
        }

        car.setVin(carDto.vin());
        car.setRegistrationNumber(carDto.registrationNumber());
        car.setMileageKm(carDto.mileageKm());
        car.setProductionYear(carDto.productionYear());

        if (carDto.carDetails() != null) {
            CarDetails details = car.getCarDetails();
           
            if (details == null) {
                details = new CarDetails();
                details.setCar(car);
                car.setCarDetails(details);
            }
            
            ReqCarDetailsDto d = carDto.carDetails();
            details.setEngineNumber(d.engineNumber());
            details.setRegistrationCode(d.registrationCode());
            details.setFuelType(d.fuelType());
            details.setEngineCapacity(d.engineCapacity());
            details.setColor(d.color());
            details.setInsuranceNumber(d.insuranceNumber());
        }

        carRepository.save(car);
    }

    private static RespCarDto toDto(Car car) {
        return new RespCarDto(
                car.getId(),
                car.getVin(),
                car.getRegistrationNumber(),
                car.getMileageKm(),
                car.getProductionYear(),
                car.getCarModel().getId(),
                car.getCarModel().getName(),
                car.getCarModel().getCarBrand().getId(),
                car.getCarModel().getCarBrand().getName(),
                CarDetailsMapper.toDto(car.getCarDetails())
        );
    }

    private static Car toEntity(ReqCarDto dto, CarModel carModel) {
        return Car.builder()
                .carModel(carModel)
                .vin(dto.vin())
                .registrationNumber(dto.registrationNumber())
                .mileageKm(dto.mileageKm())
                .productionYear(dto.productionYear())
                .build();
    }

    private static CarDetails toCarDetailsEntity(ReqCarDetailsDto dto, Car car) {
        return CarDetails
                .builder()
                .car(car)
                .engineNumber(dto.engineNumber())
                .registrationCode(dto.registrationCode())
                .fuelType(dto.fuelType())
                .engineCapacity(dto.engineCapacity())
                .color(dto.color())
                .insuranceNumber(dto.insuranceNumber())
                .build();
    }
}
