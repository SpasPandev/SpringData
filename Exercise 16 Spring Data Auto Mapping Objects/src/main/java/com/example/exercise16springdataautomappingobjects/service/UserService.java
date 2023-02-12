package com.example.exercise16springdataautomappingobjects.service;

import com.example.exercise16springdataautomappingobjects.model.dto.UserLoginDto;
import com.example.exercise16springdataautomappingobjects.model.dto.UserRegisterDto;
import com.example.exercise16springdataautomappingobjects.model.entity.User;

public interface UserService {

    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDto userLoginDto);

    void logoutUser();

    User getLogedInUser();
}
