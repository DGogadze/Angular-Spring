package com.angular.spring.service;

import com.angular.spring.entities.User;
import com.angular.spring.model.AuthenticationRequest;
import com.angular.spring.model.AuthenticationResponse;
import com.angular.spring.model.LoginRequest;
import com.angular.spring.repository.UserRepository;
import com.angular.spring.security.JwtTokenProvider;
import com.angular.spring.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationResponse isAuthenticated(AuthenticationRequest authenticationRequest, String token) {
        String username = authenticationRequest.getUsername();
        Optional<User> userOptional = userRepository.findByUsername(username);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String password = user.getPassword();

            authenticationResponse.setAuthenticated(token.equals(HashUtils.md5hash(username + password)));
        }
        return authenticationResponse;
    }

    public String authenticate(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

        return jwtTokenProvider.createToken(username);
    }
}
