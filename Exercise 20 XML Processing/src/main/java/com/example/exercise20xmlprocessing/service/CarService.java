package com.example.exercise20xmlprocessing.service;

import com.example.exercise20xmlprocessing.model.dto.CarsWithTheirListOfPartsRootDto;
import com.example.exercise20xmlprocessing.model.dto.ToyotaCarsRootDto;
import com.example.exercise20xmlprocessing.model.entity.Car;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface CarService {

    void seedCars(String filePath) throws JAXBException, FileNotFoundException;

    Car getRandomCar();

    ToyotaCarsRootDto findAllToyotaCarsOrderByModelThenTravelledDistanceDesc();

    CarsWithTheirListOfPartsRootDto findAllCarsWithTheirParts();
}
