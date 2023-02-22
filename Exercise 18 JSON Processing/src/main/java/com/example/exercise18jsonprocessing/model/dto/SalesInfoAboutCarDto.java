package com.example.exercise18jsonprocessing.model.dto;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class SalesInfoAboutCarDto {

    @Expose
    private CarInfoDto car;
    @Expose
    private String customerName;
    @Expose
    private double discount;
    @Expose
    private BigDecimal price;
    @Expose
    private BigDecimal priceWithDiscount;

    public SalesInfoAboutCarDto() {
    }

    public CarInfoDto getCar() {
        return car;
    }

    public void setCar(CarInfoDto car) {
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
