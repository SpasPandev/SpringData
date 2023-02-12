package com.example.exercise16springdataautomappingobjects.model.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    private User buyer;
    private List<Game> products;

    public Order() {
        this.products = new ArrayList<>();
    }

    public Order(User buyer, List<Game> products) {
        this.buyer = buyer;
        this.products = products;
    }

    @OneToOne
    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    public List<Game> getProducts() {
        return products;
    }

    public void setProducts(List<Game> products) {
        this.products = products;
    }
}
