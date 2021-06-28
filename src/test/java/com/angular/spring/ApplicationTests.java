package com.angular.spring;

import com.angular.spring.controllers.UserController;
import com.angular.spring.model.LoginRequest;
import com.angular.spring.model.LoginResponse;
import com.angular.spring.model.RegistrationUserRequest;
import com.angular.spring.model.RegistrationUserResponse;
import com.angular.spring.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ApplicationTests {
    @Autowired
    private UserController userController;

    @Test
    void userControllerTests() {
        RegistrationUserRequest registrationRequest = new RegistrationUserRequest();
        registrationRequest.setUsername("Username");
        registrationRequest.setPassword("Password");
        registrationRequest.setPosition("Java Developer");
        registrationRequest.setPhone("577-11-22-33");
        registrationRequest.setEmail("some@gmail.com");
        RegistrationUserResponse registrationUserResponse = userController.registrationUser(registrationRequest);

        assert registrationUserResponse.getOperationCode() == 0;
        assert registrationUserResponse.getOperationMessage().equals("Success");

        log.info("User successfully registered -> " + JsonUtils.parse(registrationRequest));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Username");
        loginRequest.setPassword("Password");

        LoginResponse loginResponse = userController.login(loginRequest);
        assert loginResponse.getOperationCode() == 0;
        assert loginResponse.getUsername().equals("Username");

        log.info("User successfully logged in -> " + JsonUtils.parse(loginResponse));
    }

}
