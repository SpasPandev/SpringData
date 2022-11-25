package com.example.lab11springdataintro.services;

import com.example.lab11springdataintro.models.User;
import com.example.lab11springdataintro.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(User user) {

        userRepository.save(user);
    }
}
