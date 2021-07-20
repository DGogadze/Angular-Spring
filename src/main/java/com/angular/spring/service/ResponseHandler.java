package com.angular.spring.service;

import com.angular.spring.entities.User;
import com.angular.spring.enums.LoginEnums;
import com.angular.spring.enums.RegistrationEnums;
import com.angular.spring.model.AuthenticationResponse;
import com.angular.spring.model.LoginResponse;
import com.angular.spring.model.RegistrationUserResponse;
import com.angular.spring.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseHandler {
    @Autowired
    public ResponseHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private final JwtTokenProvider jwtTokenProvider;

    public RegistrationUserResponse handleRegistrationResponse(RegistrationEnums status) {
        RegistrationUserResponse registrationUserResponse = new RegistrationUserResponse();
        switch (status) {
            case SUCCESS: {
                registrationUserResponse.setOperationCode(0);
                registrationUserResponse.setOperationMessage("Success");
                break;
            }
            case USER_EXIST: {
                registrationUserResponse.setOperationCode(2);
                registrationUserResponse.setOperationMessage("User with this username or email exist");
                break;
            }
            default: {
                registrationUserResponse.setOperationCode(2000);
                registrationUserResponse.setOperationMessage("GENERAL ERROR");
            }
        }
        return registrationUserResponse;
    }

    public LoginResponse handleLoginResponse(LoginEnums status, User user) {
        LoginResponse loginResponse = new LoginResponse();
        switch (status) {
            case SUCCESS: {
                if (user == null) {
                    loginResponse.setOperationCode(2001);
                    loginResponse.setOperationMessage("USER NOT PRESENTED");
                    break;
                }
                loginResponse.setOperationCode(0);
                loginResponse.setOperationMessage("Success");
                loginResponse.setUsername(user.getUsername());
                loginResponse.setToken(jwtTokenProvider.createToken(user.getUsername()));
                break;
            }
            case INVALID_CREDENTIALS: {
                loginResponse.setOperationCode(3);
                loginResponse.setOperationMessage("Invalid credentials");
                break;
            }
        }
        return loginResponse;
    }

    public AuthenticationResponse handleAuthenticationResponse(String token){
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        boolean isTokenValid = jwtTokenProvider.validateToken(token);
        authenticationResponse.setAuthenticated(isTokenValid);
        return authenticationResponse;
    }
}
