package com.example.exercise18jsonprocessing.model.dto;

import com.example.exercise18jsonprocessing.model.entity.Sale;
import com.google.gson.annotations.Expose;

import java.util.Set;

public class OrderedCustomersDto {

    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private String birthDate;
    @Expose
    private boolean isYoungDriver;
    @Expose
    private Set<Sale> sales;

    public OrderedCustomersDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
