package com.example.exercise18jsonprocessing.model.dto;

import com.example.exercise18jsonprocessing.model.entity.User;
import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductNameAndPriceDto {

    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private String seller;

    public ProductNameAndPriceDto() {
    }

    @Size(min = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
