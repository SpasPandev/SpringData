package com.example.exercise20xmlprocessing.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity{

    private Car car;
    private Customer customer;
    private Double discount;

    public Sale() {
    }

    @OneToOne
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Column
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
