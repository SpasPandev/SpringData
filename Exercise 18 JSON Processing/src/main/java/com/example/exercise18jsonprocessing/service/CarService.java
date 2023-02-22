package com.example.exercise18jsonprocessing.service;

import com.example.exercise18jsonprocessing.model.dto.CarsAndPartsDto;
import com.example.exercise18jsonprocessing.model.dto.CarsFromMakeInfoDto;
import com.example.exercise18jsonprocessing.model.entity.Car;

import java.io.IOException;
import java.util.List;

public interface CarService {
    void seedCars() throws IOException;

    Car getRandomCar();

    List<CarsFromMakeInfoDto> findAllCarsFromMakeToyotaAndOrderThemByModelThenTravelledDistanceDesc();

    List<CarsAndPartsDto> findAllCarsWithTheirParts();
}
