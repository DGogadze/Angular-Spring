package com.angular.spring.service;

import com.angular.spring.entities.User;
import com.angular.spring.enums.LoginEnums;
import com.angular.spring.enums.RegistrationEnums;
import com.angular.spring.model.LoginRequest;
import com.angular.spring.model.LoginResponse;
import com.angular.spring.model.RegistrationUserRequest;
import com.angular.spring.model.RegistrationUserResponse;
import com.angular.spring.repository.UserRepository;
import com.angular.spring.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public UserService(UserRepository userRepository, ResponseHandler responseHandler) {
        this.userRepository = userRepository;
        this.responseHandler = responseHandler;
    }

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final UserRepository userRepository;
    private final ResponseHandler responseHandler;

    private final Logger LOG = LoggerFactory.getLogger(UserService.class);

    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        LoginResponse loginResponse;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (bCryptPasswordEncoder().matches(password, user.getPassword())) {
                loginResponse = responseHandler.handleLoginResponse(LoginEnums.SUCCESS, user);
            } else loginResponse = responseHandler.handleLoginResponse(LoginEnums.INVALID_CREDENTIALS, null);
        } else {
            loginResponse = responseHandler.handleLoginResponse(LoginEnums.INVALID_CREDENTIALS, null);
        }
        return loginResponse;
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
            LOG.info("User registration failed -> " + JsonUtils.parse(registrationUserRequest) + " => " + JsonUtils.parse(registrationUserResponse));
        } else {
            registrationUserResponse = responseHandler.handleRegistrationResponse(RegistrationEnums.SUCCESS);

            User user = new User();
            user.setUsername(username);
            user.setPassword(bCryptPasswordEncoder().encode(password));
            user.setEmail(email);
            user.setPhone(phone);
            user.setPosition(position);
            userRepository.save(user);
            LOG.info("User registration -> " + JsonUtils.parse(registrationUserRequest));
        }
        return registrationUserResponse;
    }
}
