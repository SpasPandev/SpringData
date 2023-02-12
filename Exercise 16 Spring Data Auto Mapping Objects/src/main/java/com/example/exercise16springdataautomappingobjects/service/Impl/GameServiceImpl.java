package com.example.exercise16springdataautomappingobjects.service.Impl;

import com.example.exercise16springdataautomappingobjects.model.dto.GameAddDto;
import com.example.exercise16springdataautomappingobjects.model.dto.GameViewDto;
import com.example.exercise16springdataautomappingobjects.model.entity.Game;
import com.example.exercise16springdataautomappingobjects.repository.GameRepository;
import com.example.exercise16springdataautomappingobjects.service.GameService;
import com.example.exercise16springdataautomappingobjects.service.UserService;
import com.example.exercise16springdataautomappingobjects.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, ValidationUtil validationUtil, UserService userService) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
    }

    @Override
    public void addGame(GameAddDto gameAddDto) {

        Set<ConstraintViolation<GameAddDto>> violations = validationUtil.getViolations(gameAddDto);

        if (!violations.isEmpty()) {

            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        Game game = modelMapper.map(gameAddDto, Game.class);

        gameRepository.save(game);

        System.out.println("Added " + gameAddDto.getTitle());
    }

    @Override
    public void editGame(Long id, List<String[]> values) {

        Game game = gameRepository.findById(id).orElse(null);

        if (game == null) {

            System.out.println("Enter a valid game id.");

            return;
        }

        values.forEach(value -> {
            switch (value[0]){
                case "title" -> game.setTitle(value[1]);
                case "trailer" -> game.setTrailer(value[1]);
                case "imageThumbnail" -> game.setImageThumbnail(value[1]);
                case "size" -> game.setSize(Double.parseDouble(value[1]));
                case "price" -> game.setPrice(new BigDecimal(value[1]));
                case "description" -> game.setDescription(value[1]);
                case "releaseDate" -> game.setReleaseDate(LocalDate.parse(value[1],
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
        });

        gameRepository.save(game);
        System.out.println("Edited " + game.getTitle());
    }

    @Override
    public void deleteGame(Long id) {

        Game game = gameRepository.findById(id).orElse(null);

        if (game == null) {

            System.out.println("Enter a valid game id.");

            return;
        }

        gameRepository.delete(game);

    }

    @Override
    public void printAllGamesAndPrices() {

        gameRepository
                .findAll()
                .stream()
                .map(game -> String.format(game.getTitle() + " " + game.getPrice()))
                .forEach(System.out::println);
    }

    @Override
    public void printGameDetails(GameViewDto gameViewDto) {

        Set<ConstraintViolation<GameViewDto>> violations = validationUtil.getViolations(gameViewDto);

        if  (!violations.isEmpty()) {

            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        Game game = gameRepository.findByTitle(gameViewDto.getTitle());

        System.out.println("Title: " + game.getTitle() + "\n" +
                "Price: " + game.getPrice() + "\n" +
                "Description: " + game.getDescription() + "\n" +
                "Release Date: " + game.getReleaseDate() + "\n");
    }

    @Override
    public void printOwnedGames() {

        if (userService.getLogedInUser() == null) {

            System.out.println("There is not loged in user.");
            return;
        }

        userService
               .getLogedInUser()
               .getGames()
               .stream()
               .map(Game::getTitle)
               .forEach(System.out::println);

    }

}
