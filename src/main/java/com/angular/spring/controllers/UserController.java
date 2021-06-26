package com.angular.spring.controllers;

import com.angular.spring.entities.User;
import com.angular.spring.model.*;
import com.angular.spring.security.JwtTokenProvider;
import com.angular.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {
    @Autowired
    public UserController(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        String token = jwtTokenProvider.createToken(username);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(username);
        loginResponse.setToken(token);
        loginResponse.setOperationCode(0);
        loginResponse.setOperationMessage("Success");
        return loginResponse;
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