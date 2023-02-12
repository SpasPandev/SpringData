package com.example.exercise16springdataautomappingobjects.service;

import com.example.exercise16springdataautomappingobjects.model.dto.GameAddDto;
import com.example.exercise16springdataautomappingobjects.model.dto.GameViewDto;

import java.util.List;

public interface GameService {
    void addGame(GameAddDto gameAddDto);

    void editGame(Long id, List<String[]> values);

    void deleteGame(Long id);

    void printAllGamesAndPrices();

    void printGameDetails(GameViewDto gameViewDto);

    void printOwnedGames();
}
