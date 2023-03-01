package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class UsersAndProductsDto {

    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlAttribute(name = "age")
    private Integer age;
    @XmlElement(name = "sold-products")
    private SoldProductsRootDto soldProducts;

    public UsersAndProductsDto() {
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

    public SoldProductsRootDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(SoldProductsRootDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
