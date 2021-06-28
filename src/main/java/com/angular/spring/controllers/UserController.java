package com.angular.spring.controllers;

import com.angular.spring.model.LoginRequest;
import com.angular.spring.model.LoginResponse;
import com.angular.spring.model.RegistrationUserRequest;
import com.angular.spring.model.RegistrationUserResponse;
import com.angular.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/registration")
    public RegistrationUserResponse registrationUser(@RequestBody RegistrationUserRequest registrationUserRequest) {
        return userService.registration(registrationUserRequest);
    }
}