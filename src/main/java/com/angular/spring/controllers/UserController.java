package com.angular.spring.controllers;

import com.angular.spring.model.LoginRequest;
import com.angular.spring.model.LoginResponse;
import com.angular.spring.model.RegistrationUserRequest;
import com.angular.spring.model.RegistrationUserResponse;
import com.angular.spring.service.AuthenticationService;
import com.angular.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {
    @Autowired
    public UserController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String token = authenticationService.authenticate(loginRequest);
        return userService.login(loginRequest, token);
    }

    @PostMapping("/registration")
    public RegistrationUserResponse registrationUser(@RequestBody RegistrationUserRequest registrationUserRequest) {
        return userService.registration(registrationUserRequest);
    }
}