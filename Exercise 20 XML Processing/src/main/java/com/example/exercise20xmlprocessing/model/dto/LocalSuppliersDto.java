package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "supplier")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class LocalSuppliersDto {

    @XmlAttribute(name = "id")
    private Long id;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "parts-count")
    private Integer partsCount;

    public LocalSuppliersDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Integer partsCount) {
        this.partsCount = partsCount;
    }
}
