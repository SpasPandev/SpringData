package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sold=products")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class SoldProductsRootDto {

    @XmlAttribute(name = "count")
    private Integer count;
    @XmlElement(name = "product")
    private List<SoldProductsDto> soldProducts;

    public SoldProductsRootDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<SoldProductsDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProductsDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
