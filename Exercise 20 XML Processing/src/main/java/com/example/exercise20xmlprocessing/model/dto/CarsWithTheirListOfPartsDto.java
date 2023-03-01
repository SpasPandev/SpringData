package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "car")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CarsWithTheirListOfPartsDto {

    @XmlAttribute(name = "make")
    private String make;
    @XmlAttribute(name = "model")
    private String model;
    @XmlAttribute(name = "travelledDistance")
    private Long travelledDistance;
    @XmlElement(name = "parts")
    private PartsNameAndPriceRootDto parts;

    public CarsWithTheirListOfPartsDto() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    public PartsNameAndPriceRootDto getParts() {
        return parts;
    }

    public void setParts(PartsNameAndPriceRootDto parts) {
        this.parts = parts;
    }
}
