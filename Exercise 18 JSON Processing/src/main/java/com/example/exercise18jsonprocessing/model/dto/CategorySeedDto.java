package com.example.exercise18jsonprocessing.model.dto;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Size;

public class CategorySeedDto {

    @Expose
    private String name;

    public CategorySeedDto() {
    }

    @Size(min = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
