package com.example.exercise18jsonprocessing.model.dto;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Positive;

public class CarSeedDto {

    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private Long travelledDistance;

    public CarSeedDto() {
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

    @Positive
    public Long getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Long travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}
