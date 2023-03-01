package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ToyotaCarsRootDto {

    @XmlElement(name = "car")
    private List<ToyotaCarsDto> cars;

    public ToyotaCarsRootDto() {
    }

    public List<ToyotaCarsDto> getCars() {
        return cars;
    }

    public void setCars(List<ToyotaCarsDto> cars) {
        this.cars = cars;
    }
}
