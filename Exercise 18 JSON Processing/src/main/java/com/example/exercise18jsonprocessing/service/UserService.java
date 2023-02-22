package com.example.exercise18jsonprocessing.service;

import com.example.exercise18jsonprocessing.model.dto.UserSoldProductsDto;
import com.example.exercise18jsonprocessing.model.dto.UsersAndProductsDto;
import com.example.exercise18jsonprocessing.model.entity.User;

import java.io.IOException;
import java.util.List;

public interface UserService {

    void seedUsers() throws IOException;

    User getRandomUser();

    List<UserSoldProductsDto> findAllSuccessfullySoldProducts();

    UsersAndProductsDto findAllWithSoldProductsOrderThemBySoldProductsDesc();
}
