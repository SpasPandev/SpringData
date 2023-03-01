package com.example.exercise20xmlprocessing.service;

import com.example.exercise20xmlprocessing.model.dto.UserSoldProductsRootDto;
import com.example.exercise20xmlprocessing.model.dto.UsersAndProductsRootDto;
import com.example.exercise20xmlprocessing.model.entity.User;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface UserService {

    void seedUsers(String filePath) throws JAXBException, FileNotFoundException;

    User getRandomUser();

    UserSoldProductsRootDto findAllWithSoldProductsAndBuyer();

    UsersAndProductsRootDto findAllWithSoldProducts();
}
