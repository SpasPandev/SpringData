package com.example.exercise18jsonprocessing.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity{

    private Car car;
    private Customer customer;
    private double discount;

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
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
