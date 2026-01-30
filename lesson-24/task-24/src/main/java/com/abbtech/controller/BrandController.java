package com.abbtech.controller;

import com.abbtech.dto.car_brand.ReqCarBrandDto;
import com.abbtech.dto.car_brand.RespCarBrandDto;
import com.abbtech.service.car_brand.CarBrandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final CarBrandService brandService;

    public BrandController(CarBrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public List<RespCarBrandDto> getBrands() {
        return brandService.getBrands();
    }

    @GetMapping("/{id}")
    public RespCarBrandDto getBrandById(@PathVariable Integer id) {
        return brandService.getBrandById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBrand(@Valid @RequestBody ReqCarBrandDto reqBrandDto) {
        brandService.addBrand(reqBrandDto);
    }

    @PutMapping("/{id}")
    public void updateBrand(@PathVariable Integer id, @Valid @RequestBody ReqCarBrandDto reqBrandDto) {
        brandService.updateBrand(id, reqBrandDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBrand(@PathVariable Integer id) {
        brandService.deleteBrandById(id);
    }
}
