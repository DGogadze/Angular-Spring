package com.angular.spring.service;

import com.angular.spring.entities.User;
import com.angular.spring.enums.LoginEnums;
import com.angular.spring.enums.RegistrationEnums;
import com.angular.spring.model.LoginRequest;
import com.angular.spring.model.LoginResponse;
import com.angular.spring.model.RegistrationUserRequest;
import com.angular.spring.model.RegistrationUserResponse;
import com.angular.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public UserService(UserRepository userRepository, ResponseHandler responseHandler, AuthenticationManager authenticationManager,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.responseHandler = responseHandler;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private final UserRepository userRepository;
    private final ResponseHandler responseHandler;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return responseHandler.handleLoginResponse(LoginEnums.SUCCESS, user);
        }
        return responseHandler.handleLoginResponse(LoginEnums.INVALID_CREDENTIALS, null);
    }

    public RegistrationUserResponse registration(RegistrationUserRequest registrationUserRequest) {
        String username = registrationUserRequest.getUsername();
        String email = registrationUserRequest.getEmail();
        String password = registrationUserRequest.getPassword();
        String phone = registrationUserRequest.getPhone();
        String position = registrationUserRequest.getPosition();

        RegistrationUserResponse registrationUserResponse;
        if (userRepository.findUserByUsername(username).isPresent() || userRepository.findUserByEmail(email).isPresent()) {
            registrationUserResponse = responseHandler.handleRegistrationResponse(RegistrationEnums.USER_EXIST);
        } else {
            registrationUserResponse = responseHandler.handleRegistrationResponse(RegistrationEnums.SUCCESS);

            User user = new User();
            user.setUsername(username);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user.setEmail(email);
            user.setPhone(phone);
            user.setPosition(position);
            userRepository.save(user);
        }
        return registrationUserResponse;
    }
}
