package com.example.workshop_mvc_project.service;

import com.example.workshop_mvc_project.model.dto.UserLoginDto;
import com.example.workshop_mvc_project.model.dto.UserRegisterDto;

public interface UserService {

    boolean registerUser(UserRegisterDto userRegisterDto);

    boolean logInUser(UserLoginDto userLoginDto);
}
