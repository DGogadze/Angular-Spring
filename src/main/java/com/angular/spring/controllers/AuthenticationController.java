package com.angular.spring.controllers;

import com.angular.spring.model.AuthenticationRequest;
import com.angular.spring.model.AuthenticationResponse;
import com.angular.spring.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    private final AuthenticationService authenticationService;

    @PostMapping("/auth")
    public AuthenticationResponse authenticationResponse(@RequestBody AuthenticationRequest authenticationRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return authenticationService.isAuthenticated(authenticationRequest,token);
    }
}
