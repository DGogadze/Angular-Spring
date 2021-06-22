package com.angular.spring.controllers;

import com.angular.spring.model.*;
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

    @PostMapping("/user/get")
    public GetUserResponse getUserInfo(@RequestBody GetUserRequest getUserRequest) {
        return userService.findUser(getUserRequest);
    }

    @PostMapping("/user/registration")
    public RegistrationUserResponse registrationUser(@RequestBody RegistrationUserRequest registrationUserRequest) {
        return userService.registration(registrationUserRequest);
    }
}