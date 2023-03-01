package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SalesInfoRootDto {

    @XmlElement(name = "sale")
    private List<SalesInfoDto> sales;

    public SalesInfoRootDto() {
    }

    public List<SalesInfoDto> getSales() {
        return sales;
    }

    public void setSales(List<SalesInfoDto> sales) {
        this.sales = sales;
    }
}
