package com.abbtech.service.car_brand;

import com.abbtech.dto.car_brand.ReqCarBrandDto;
import com.abbtech.dto.car_brand.RespCarBrandDto;

import java.util.List;

public interface CarBrandService {

    List<RespCarBrandDto> getBrands();

    RespCarBrandDto getBrandById(int id);

    void addBrand(ReqCarBrandDto brandDto);

    void deleteBrandById(int id);

    void updateBrand(int id, ReqCarBrandDto brandDto);
}
