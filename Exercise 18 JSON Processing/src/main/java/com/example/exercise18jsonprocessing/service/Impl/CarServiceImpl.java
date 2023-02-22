package com.example.exercise18jsonprocessing.service.Impl;

import com.example.exercise18jsonprocessing.model.dto.CarSeedDto;
import com.example.exercise18jsonprocessing.model.dto.CarsAndPartsDto;
import com.example.exercise18jsonprocessing.model.dto.CarsFromMakeInfoDto;
import com.example.exercise18jsonprocessing.model.entity.Car;
import com.example.exercise18jsonprocessing.repository.CarRepository;
import com.example.exercise18jsonprocessing.service.CarService;
import com.example.exercise18jsonprocessing.service.PartService;
import com.example.exercise18jsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.exercise18jsonprocessing.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class CarServiceImpl implements CarService {

    private static final String CARS_FILE_NAME = "cars.json";
    private final CarRepository carRepository;
    private final PartService partService;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    public CarServiceImpl(CarRepository carRepository, PartService partService, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.carRepository = carRepository;
        this.partService = partService;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedCars() throws IOException {

        if (carRepository.count() > 0) {
            return;
        }

        Arrays.stream(gson.fromJson(Files.readString(Path.of(RESOURCES_FILE_PATH +
                CARS_FILE_NAME)), CarSeedDto[].class))
                .filter(validationUtil::isValid)
                .map(carSeedDto -> {
                    Car car = modelMapper.map(carSeedDto, Car.class);
                    car.setParts(partService.getRandomParts());

                    return car;
                })
                .forEach(carRepository::save);
    }

    @Override
    public Car getRandomCar() {


        long randomId = ThreadLocalRandom.current().nextLong(1, carRepository.count() + 1);

        return carRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<CarsFromMakeInfoDto> findAllCarsFromMakeToyotaAndOrderThemByModelThenTravelledDistanceDesc() {

        return carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream()
                .map(car -> modelMapper.map(car, CarsFromMakeInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarsAndPartsDto> findAllCarsWithTheirParts() {

        return carRepository.findAll()
                .stream()
                .map(car -> {
                    CarsAndPartsDto carsAndPartsDto = modelMapper.map(car, CarsAndPartsDto.class);

                    return carsAndPartsDto;
                })
                .collect(Collectors.toList());
    }
}
