package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CarsWithTheirListOfPartsRootDto {

    @XmlElement(name = "car")
    List<CarsWithTheirListOfPartsDto> cars;

    public CarsWithTheirListOfPartsRootDto() {
    }

    public List<CarsWithTheirListOfPartsDto> getCars() {
        return cars;
    }

    public void setCars(List<CarsWithTheirListOfPartsDto> cars) {
        this.cars = cars;
    }
}
