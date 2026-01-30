package com.abbtech.controller;

import com.abbtech.dto.car.ReqCarDto;
import com.abbtech.dto.car.RespCarDto;
import com.abbtech.service.car.CarService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<RespCarDto> getCars() {
        return carService.getCars();
    }

    @GetMapping("/{id}")
    public RespCarDto getCarById(@PathVariable Integer id) {
        return carService.getCarById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCar(@Valid @RequestBody ReqCarDto reqCarDto) {
        carService.addCar(reqCarDto);
    }

    @PutMapping("/{id}")
    public void updateCar(@PathVariable Integer id, @Valid @RequestBody ReqCarDto reqCarDto) {
        carService.updateCar(id, reqCarDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable Integer id) {
        carService.deleteCarById(id);
    }
}
