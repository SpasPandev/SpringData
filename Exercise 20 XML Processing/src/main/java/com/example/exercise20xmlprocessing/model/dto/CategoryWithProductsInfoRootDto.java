package com.example.exercise20xmlprocessing.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CategoryWithProductsInfoRootDto {

    @XmlElement(name = "category")
    private List<CategoryWithProductsInfoDto> categories;

    public CategoryWithProductsInfoRootDto() {
    }

    public List<CategoryWithProductsInfoDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryWithProductsInfoDto> categories) {
        this.categories = categories;
    }
}
