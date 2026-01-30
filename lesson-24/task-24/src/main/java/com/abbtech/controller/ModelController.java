package com.abbtech.controller;

import com.abbtech.dto.car_model.ReqCarModelDto;
import com.abbtech.dto.car_model.RespCarModelDto;
import com.abbtech.service.car_model.CarModelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/models")
public class ModelController {

    private final CarModelService modelService;

    public ModelController(CarModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping
    public List<RespCarModelDto> getModels() {
        return modelService.getModels();
    }

    @GetMapping("/{id}")
    public RespCarModelDto getModelById(@PathVariable Integer id) {
        return modelService.getModelById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addModel(@Valid @RequestBody ReqCarModelDto reqModelDto) {
        modelService.addModel(reqModelDto);
    }

    @PutMapping("/{id}")
    public void updateModel(@PathVariable Integer id, @Valid @RequestBody ReqCarModelDto reqModelDto) {
        modelService.updateModel(id, reqModelDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteModel(@PathVariable Integer id) {
        modelService.deleteModelById(id);
    }
}
