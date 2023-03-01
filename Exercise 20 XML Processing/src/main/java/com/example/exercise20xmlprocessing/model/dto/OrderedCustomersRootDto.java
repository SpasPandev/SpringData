package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class OrderedCustomersRootDto {

    @XmlElement(name = "customer")
    private List<OrderedCustomersDto> customers;

    public OrderedCustomersRootDto() {
    }

    public List<OrderedCustomersDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<OrderedCustomersDto> customers) {
        this.customers = customers;
    }
}
