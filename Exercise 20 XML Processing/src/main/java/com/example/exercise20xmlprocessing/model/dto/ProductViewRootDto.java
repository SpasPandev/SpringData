package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ProductViewRootDto {

    @XmlElement(name = "product")
    private List<ProductNamePriceAndSellerDto> products;

    public ProductViewRootDto() {
    }

    public List<ProductNamePriceAndSellerDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductNamePriceAndSellerDto> products) {
        this.products = products;
    }
}
