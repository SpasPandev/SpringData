package org.example.entities.Ex5_Bills_Payment_System;

import org.example.entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "billing_details")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class BillingDetails extends BaseEntity {

    private Integer number;
    private BankUser owner;

    public BillingDetails() {
    }

    @Column(name = "number", nullable = false, unique = true)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @ManyToOne
    public BankUser getOwner() {
        return owner;
    }

    public void setOwner(BankUser owner) {
        this.owner = owner;
    }
}
