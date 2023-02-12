package com.example.exercise16springdataautomappingobjects.service;

import com.example.exercise16springdataautomappingobjects.model.dto.GameViewDto;

public interface OrderService {
    void addItem(GameViewDto gameViewDto);

    void removeItem(GameViewDto gameViewDto);

    void buyItems();
}
