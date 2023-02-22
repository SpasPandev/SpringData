package com.example.exercise18jsonprocessing.model.dto;

import com.google.gson.annotations.Expose;

public class LocalSuppliersInfoDto {

    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private Integer partsCount;

    public LocalSuppliersInfoDto() {
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
