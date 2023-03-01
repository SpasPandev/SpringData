package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.*;
import java.time.LocalDate;

@XmlRootElement(name = "customer")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CustomerSeedDto {

    @XmlAttribute(name = "name")
    private String name;
    @XmlElement(name = "birth-date")
    private String birthDate;
    @XmlElement(name = "is-young-driver")
    private boolean isYoungDriver;

    public CustomerSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
