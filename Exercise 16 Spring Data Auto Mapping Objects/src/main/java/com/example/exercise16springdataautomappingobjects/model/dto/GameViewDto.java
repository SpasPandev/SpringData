package com.example.exercise16springdataautomappingobjects.model.dto;

import jakarta.validation.constraints.Pattern;

public class GameViewDto {

    private String title;

    public GameViewDto() {
    }

    public GameViewDto(String title) {
        this.title = title;
    }

    @Pattern(regexp = "^[A-Z]{1}.{2,99}$", message = "Enter valid title.")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
