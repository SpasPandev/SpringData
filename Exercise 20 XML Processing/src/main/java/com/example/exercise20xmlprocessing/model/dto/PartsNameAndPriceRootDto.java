package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class PartsNameAndPriceRootDto {

    @XmlElement(name = "part")
    private List<PartsNameAndPriceDto> parts;

    public PartsNameAndPriceRootDto() {
    }

    public List<PartsNameAndPriceDto> getParts() {
        return parts;
    }

    public void setParts(List<PartsNameAndPriceDto> parts) {
        this.parts = parts;
    }
}
