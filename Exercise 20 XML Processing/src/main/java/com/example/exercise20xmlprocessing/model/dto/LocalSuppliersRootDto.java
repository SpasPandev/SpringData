package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class LocalSuppliersRootDto {

    @XmlElement(name = "supplier")
    List<LocalSuppliersDto> suppliers;

    public LocalSuppliersRootDto() {
    }

    public List<LocalSuppliersDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<LocalSuppliersDto> suppliers) {
        this.suppliers = suppliers;
    }
}
