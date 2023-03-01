package com.example.exercise20xmlprocessing.service.Impl;

import com.example.exercise20xmlprocessing.model.dto.*;
import com.example.exercise20xmlprocessing.model.entity.Car;
import com.example.exercise20xmlprocessing.repository.CarRepository;
import com.example.exercise20xmlprocessing.service.CarService;
import com.example.exercise20xmlprocessing.service.PartService;
import com.example.exercise20xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final PartService partService;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public CarServiceImpl(CarRepository carRepository, PartService partService, ModelMapper modelMapper, XmlParser xmlParser) {
        this.carRepository = carRepository;
        this.partService = partService;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCars(String filePath) throws JAXBException, FileNotFoundException {

        if (carRepository.count() > 0) {
            return;
        }

        CarSeedRootDto carSeedRootDto = xmlParser.fromFile(filePath, CarSeedRootDto.class);

        carSeedRootDto.getCars()
                .stream()
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
    public ToyotaCarsRootDto findAllToyotaCarsOrderByModelThenTravelledDistanceDesc() {

        List<Car> cars = carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota");

        ToyotaCarsRootDto toyotaCarsRootDto = new ToyotaCarsRootDto();

        toyotaCarsRootDto.setCars(cars
                .stream()
                .map(car -> modelMapper.map(car, ToyotaCarsDto.class))
                .collect(Collectors.toList()));

        return toyotaCarsRootDto;
    }

    @Override
    public CarsWithTheirListOfPartsRootDto findAllCarsWithTheirParts() {

        List<Car> cars = carRepository.findAll();

        CarsWithTheirListOfPartsRootDto carsWithTheirListOfPartsRootDto = new CarsWithTheirListOfPartsRootDto();

        carsWithTheirListOfPartsRootDto.setCars(cars
                .stream()
                .map(car -> modelMapper.map(car, CarsWithTheirListOfPartsDto.class))
                .collect(Collectors.toList()));

        return carsWithTheirListOfPartsRootDto;
    }
}
