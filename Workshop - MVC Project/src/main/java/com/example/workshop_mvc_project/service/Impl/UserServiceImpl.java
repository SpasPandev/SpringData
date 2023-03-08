package com.example.workshop_mvc_project.service.Impl;

import com.example.workshop_mvc_project.model.dto.UserLoginDto;
import com.example.workshop_mvc_project.model.dto.UserRegisterDto;
import com.example.workshop_mvc_project.model.entity.User;
import com.example.workshop_mvc_project.repository.UserRepository;
import com.example.workshop_mvc_project.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registerUser(UserRegisterDto userRegisterDto) {

        if (userRepository.existsByUsernameAndEmail(userRegisterDto.getUsername(),
                userRegisterDto.getEmail())) {

            return false;
        }

        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())) {

            return false;
        }

        var user = modelMapper.map(userRegisterDto, User.class);

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean logInUser(UserLoginDto userLoginDto) {

        if (userRepository.findByUsernameAndPassword(userLoginDto.getUsername(),
                userLoginDto.getPassword()) == null) {


            return false;
        }

        return true;
    }
}
