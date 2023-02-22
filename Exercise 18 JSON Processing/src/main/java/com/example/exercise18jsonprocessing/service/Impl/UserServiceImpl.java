package com.example.exercise18jsonprocessing.service.Impl;

import com.example.exercise18jsonprocessing.model.dto.*;
import com.example.exercise18jsonprocessing.model.entity.User;
import com.example.exercise18jsonprocessing.repository.UserRepository;
import com.example.exercise18jsonprocessing.service.UserService;
import com.example.exercise18jsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import static com.example.exercise18jsonprocessing.constants.GlobalConstants.RESOURCES_FILE_PATH;

@Service
public class UserServiceImpl implements UserService {

    private static final String USERS_FILE_NAME = "users.json";
    private final UserRepository userRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedUsers() throws IOException {

        if (userRepository.count() > 0) {
            return;
        }

        Arrays.stream(gson.fromJson(Files.readString(Path.of(RESOURCES_FILE_PATH + USERS_FILE_NAME)), UserSeedDto[].class))
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {

        long randomId = ThreadLocalRandom.current().nextLong(1l, userRepository.count() + 1);

        return userRepository.findById(randomId).orElse(null);
    }

    @Override
    public List<UserSoldProductsDto> findAllSuccessfullySoldProducts() {

        return userRepository
                .findAllUserWithMoreThanOneSoldProductsOrderByLastNameThenByFirstName()
                .stream()
                .map(user -> modelMapper.map(user, UserSoldProductsDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsersAndProductsDto findAllWithSoldProductsOrderThemBySoldProductsDesc() {

        List<User> users = userRepository.findAllBySoldProductsIsNotNullOrderBySoldProductsDescLastName();

        UsersAndProductsDto usersAndProductsDto = new UsersAndProductsDto();
        usersAndProductsDto.setUsersCount(users.size());

        List<UsersFirstAndLastNameAgeDto> collect = users
                .stream()
                .map(user -> {
                    UsersFirstAndLastNameAgeDto mappedUser = modelMapper.map(user, UsersFirstAndLastNameAgeDto.class);
                    mappedUser.getSoldProducts().setCount(user.getSoldProducts().size());

                    return mappedUser;
                })
                .collect(Collectors.toList());

        usersAndProductsDto.setUsers(collect);

        return usersAndProductsDto;
    }
}
