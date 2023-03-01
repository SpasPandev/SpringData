package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class TotalSalesByCustomerRootDto {

    @XmlElement(name = "customer")
    private List<TotalSalesByCustomerDto> customers;

    public TotalSalesByCustomerRootDto() {
    }

    public List<TotalSalesByCustomerDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<TotalSalesByCustomerDto> customers) {
        this.customers = customers;
    }
}
