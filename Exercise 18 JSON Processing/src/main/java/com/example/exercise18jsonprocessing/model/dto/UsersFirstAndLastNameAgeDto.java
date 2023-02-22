package com.example.exercise18jsonprocessing.model.dto;

import com.google.gson.annotations.Expose;

public class UsersFirstAndLastNameAgeDto {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer age;
    @Expose
    private SoldProductsAndCountDto soldProducts;

    public UsersFirstAndLastNameAgeDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public SoldProductsAndCountDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(SoldProductsAndCountDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
