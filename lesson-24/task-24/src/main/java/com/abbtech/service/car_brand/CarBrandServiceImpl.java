package com.abbtech.service.car_brand;

import com.abbtech.dto.car_brand.ReqCarBrandDto;
import com.abbtech.dto.car_brand.RespCarBrandDto;
import com.abbtech.exception.car.CarErrorEnum;
import com.abbtech.exception.car.CarException;
import com.abbtech.model.CarBrand;
import com.abbtech.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarBrandServiceImpl implements CarBrandService {

    private final BrandRepository brandRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RespCarBrandDto> getBrands() {
        return brandRepository.findAll().stream()
                .map(CarBrandServiceImpl::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RespCarBrandDto getBrandById(int id) {
        CarBrand carBrand = brandRepository.findById(id)
                .orElseThrow(() -> new CarException(CarErrorEnum.BRAND_NOT_FOUND));
        return toDto(carBrand);
    }

    @Override
    @Transactional
    public void addBrand(ReqCarBrandDto brandDto) {
        CarBrand carBrand = toEntity(brandDto);
        brandRepository.save(carBrand);
    }

    @Override
    @Transactional
    public void deleteBrandById(int id) {
        CarBrand carBrand = brandRepository
                .findById(id)
                .orElseThrow(() -> new CarException(CarErrorEnum.BRAND_NOT_FOUND));
        brandRepository.delete(carBrand);
    }

    @Override
    @Transactional
    public void updateBrand(int id, ReqCarBrandDto brandDto) {
        CarBrand carBrand = brandRepository
                .findById(id)
                .orElseThrow(() -> new CarException(CarErrorEnum.BRAND_NOT_FOUND));

        carBrand.setName(brandDto.name());
        carBrand.setCountry(brandDto.country());
        carBrand.setFoundedYear(brandDto.foundedYear());
        
        brandRepository.save(carBrand);
    }

    private static RespCarBrandDto toDto(CarBrand carBrand) {
        return new RespCarBrandDto(
                carBrand.getId(),
                carBrand.getName(),
                carBrand.getCountry(),
                carBrand.getFoundedYear()
        );
    }

    private static CarBrand toEntity(ReqCarBrandDto dto) {
        return CarBrand
                .builder()
                .name(dto.name())
                .country(dto.country())
                .foundedYear(dto.foundedYear())
                .build();
    }
}
