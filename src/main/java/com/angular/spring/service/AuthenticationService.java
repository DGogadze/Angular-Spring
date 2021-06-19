package com.angular.spring.service;

import com.angular.spring.entities.User;
import com.angular.spring.model.AuthenticationRequest;
import com.angular.spring.model.AuthenticationResponse;
import com.angular.spring.repository.UserRepository;
import com.angular.spring.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    UserRepository userRepository;

    public AuthenticationResponse isAuthenticated(AuthenticationRequest authenticationRequest, String token){
        String username = authenticationRequest.getUsername();
        Optional<User> userOptional = userRepository.findByUsername(username);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (userOptional.isPresent()){
            User user = userOptional.get();
            String password = user.getPassword();

            authenticationResponse.setAuthenticated(token.equals(HashUtils.md5hash(username + password)));
        }
        return authenticationResponse;
    }
}
