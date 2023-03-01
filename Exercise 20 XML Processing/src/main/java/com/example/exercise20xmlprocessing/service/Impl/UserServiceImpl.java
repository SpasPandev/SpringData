package com.example.exercise20xmlprocessing.service.Impl;

import com.example.exercise20xmlprocessing.model.dto.*;
import com.example.exercise20xmlprocessing.model.entity.User;
import com.example.exercise20xmlprocessing.repository.UserRepository;
import com.example.exercise20xmlprocessing.service.UserService;
import com.example.exercise20xmlprocessing.util.ValidationUtil;
import com.example.exercise20xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public void seedUsers(String filePath) throws JAXBException, FileNotFoundException {

        if (userRepository.count() > 0) {
            return;
        }

        UserSeedRootDto userSeedRootDto = xmlParser.fromFile(filePath, UserSeedRootDto.class);

        userSeedRootDto.getUsers()
                .stream()
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {

        return userRepository.findById(ThreadLocalRandom.current().nextLong(1, userRepository.count() + 1))
                .orElse(null);
    }

    @Override
    public UserSoldProductsRootDto findAllWithSoldProductsAndBuyer() {

        List<User> users = userRepository.findAllBySoldProductsIsNotNullOrderByLastNameAscFirstName();

        UserSoldProductsRootDto userSoldProductsRootDto = new UserSoldProductsRootDto();

        userSoldProductsRootDto.setUsers(users
                .stream()
                .map(user -> modelMapper.map(user, UserSoldProductsDto.class))
                .collect(Collectors.toList()));

        return userSoldProductsRootDto;
    }

    @Override
    public UsersAndProductsRootDto findAllWithSoldProducts() {

        List<User> users = userRepository.findAllBySoldProductsIsNotNullOrderBySoldProductsDescLastName();

        UsersAndProductsRootDto usersAndProductsRootDto = new UsersAndProductsRootDto();

        usersAndProductsRootDto.setCount(users.size());

        usersAndProductsRootDto.setUsers(users
                .stream()
                .map(user -> {
                    UsersAndProductsDto u = modelMapper.map(user, UsersAndProductsDto.class);

                    u.getSoldProducts().setCount(u.getSoldProducts().getSoldProducts().size());

                    return u;
                })
                .collect(Collectors.toList()));

        return usersAndProductsRootDto;
    }
}
