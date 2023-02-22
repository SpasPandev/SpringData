package com.example.exercise18jsonprocessing.model.dto;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class CarsAndPartsDto {

    @Expose
    private CarInfoDto car;
    @Expose
    private Set<PartsNameAndPriceDto> parts;

    public CarsAndPartsDto() {
    }

    public CarInfoDto getCar() {
        return car;
    }

    public void setCar(CarInfoDto car) {
        this.car = car;
    }

    public Set<PartsNameAndPriceDto> getParts() {
        return parts;
    }

    public void setParts(Set<PartsNameAndPriceDto> parts) {
        this.parts = parts;
    }
}
