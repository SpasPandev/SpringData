package com.example.workshop_mvc_project.controller;

import jakarta.servlet.http.HttpServletRequest;

public class BaseController {

    protected boolean isLoggedIn(HttpServletRequest request) {

        var user = request.getSession().getAttribute("user");

        return user != null;
    }
}
