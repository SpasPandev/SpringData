package com.example.exercise16springdataautomappingobjects.service.Impl;

import com.example.exercise16springdataautomappingobjects.model.dto.GameViewDto;
import com.example.exercise16springdataautomappingobjects.model.entity.Game;
import com.example.exercise16springdataautomappingobjects.model.entity.Order;
import com.example.exercise16springdataautomappingobjects.model.entity.User;
import com.example.exercise16springdataautomappingobjects.repository.GameRepository;
import com.example.exercise16springdataautomappingobjects.repository.OrderRepository;
import com.example.exercise16springdataautomappingobjects.repository.UserRepository;
import com.example.exercise16springdataautomappingobjects.service.OrderService;
import com.example.exercise16springdataautomappingobjects.service.UserService;
import com.example.exercise16springdataautomappingobjects.util.ValidationUtil;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ValidationUtil validationUtil, UserService userService, GameRepository gameRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addItem(GameViewDto gameViewDto) {

        Set<ConstraintViolation<GameViewDto>> violations = validationUtil.getViolations(gameViewDto);

        if (!violations.isEmpty()) {

            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        User currentUser = userService.getLogedInUser();

        if (currentUser == null) {

            System.out.println("There is not loged in user.");
            return;
        }

        List<Game> listOfGames = currentUser.getGames();

        if (listOfGames
                .stream()
                .anyMatch(game -> game.getTitle().equals(gameViewDto.getTitle()))) {

            System.out.println("This game is already bought.");
            return;
        }

        Game game = gameRepository.findByTitle(gameViewDto.getTitle());
        Order order;

        if (currentUser.getOrder() == null) {

            order = new Order();
            order.setBuyer(currentUser);
        }
        else {
            order = currentUser.getOrder();
        }

        order.getProducts().add(game);
        currentUser.setOrder(order);

        orderRepository.save(order);

        System.out.println(gameViewDto.getTitle() + " added to cart.");
    }

    @Override
    public void removeItem(GameViewDto gameViewDto) {

        Set<ConstraintViolation<GameViewDto>> violations = validationUtil.getViolations(gameViewDto);

        if (!violations.isEmpty()) {

            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);

            return;
        }

        User currentUser = userService.getLogedInUser();

        if (currentUser == null) {

            System.out.println("There is not loged in user.");
            return;
        }

        Order order = currentUser.getOrder();

        if (order == null) {

            System.out.println("Cart is empty");
            return;
        }

        List<Game> listOfProducts = order.getProducts();


        Game game = listOfProducts
                .stream()
                .filter(g ->
                        g.getTitle()
                                .equals(gameViewDto.getTitle()))
                .findAny()
                .orElse(null);
        listOfProducts.remove(game);
        orderRepository.save(order);

        if (listOfProducts.isEmpty()) {
            currentUser.setOrder(null);
            orderRepository.delete(order);
        }

        System.out.println(gameViewDto.getTitle() + " removed from cart.");
    }

    @Override
    public void buyItems() {

        User currentUser = userService.getLogedInUser();

        if (currentUser == null) {

            System.out.println("There is not loged in user.");
            return;
        }

        Order order = currentUser.getOrder();

        if (order == null) {

            System.out.println("Cart is empty");
            return;
        }

        List<Game> userGames = new ArrayList<>();

        order.getProducts()
                .forEach(game -> userGames.add(game));

        currentUser.setGames(userGames);
        userRepository.save(currentUser);

        System.out.println("Successfully bought games:");
        userGames
                .forEach(game -> {
                    System.out.println("    - " + game.getTitle());
                });
    }
}
