package org.example.entities.Ex5_Bills_Payment_System;

import javax.persistence.*;

@Entity
@Table(name = "credit_card")
public class CreditCard extends BillingDetails {

    private CardType type;
    private Integer expirationMonth;
    private Integer expirationYear;

    public CreditCard() {
    }

    @Enumerated(EnumType.STRING)
    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    @Column(name = "expiration_month", nullable = false)
    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Column(name = "expiration_year", nullable = false)
    public Integer getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }
}
