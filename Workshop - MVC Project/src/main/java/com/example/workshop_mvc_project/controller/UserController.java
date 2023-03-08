package com.example.workshop_mvc_project.controller;

import com.example.workshop_mvc_project.model.dto.UserLoginDto;
import com.example.workshop_mvc_project.model.dto.UserRegisterDto;
import com.example.workshop_mvc_project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController extends BaseController{

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/login")
    public String login() {

        return "user/login";
    }

    @PostMapping("users/login")
    public String login(UserLoginDto user, HttpServletRequest request) {

        if (!userService.logInUser(user)) {

            return "user/login";
        }

        request.getSession().setAttribute("user", user);

        return "redirect:/";
    }

    @GetMapping("/users/register")
    public String register() {

        return "user/register";
    }

    @PostMapping("/users/register")
    public String register(UserRegisterDto user, Model model) {

        if (userService.registerUser(user)) {

            return "redirect:/users/login";
        }

        return "user/register";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        if (this.isLoggedIn(request)) {

            request.getSession().setAttribute("user", null);
        }

        return "redirect:/";
    }
}
