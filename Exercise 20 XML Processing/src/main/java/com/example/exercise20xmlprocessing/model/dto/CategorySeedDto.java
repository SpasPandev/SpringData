package com.example.exercise20xmlprocessing.model.dto;

import jakarta.validation.constraints.Size;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "category")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CategorySeedDto {

    @XmlElement(name = "name")
    private String name;

    public CategorySeedDto() {
    }

    @Size(min = 3, max = 15)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
