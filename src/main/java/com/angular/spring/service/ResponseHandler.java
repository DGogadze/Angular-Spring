package com.angular.spring.service;

import com.angular.spring.entities.User;
import com.angular.spring.enums.GetUserEnums;
import com.angular.spring.enums.LoginEnums;
import com.angular.spring.enums.RegistrationEnums;
import com.angular.spring.model.GetUserResponse;
import com.angular.spring.model.LoginResponse;
import com.angular.spring.model.RegistrationUserResponse;
import com.angular.spring.model.UserInfo;
import com.angular.spring.utils.HashUtils;
import org.springframework.stereotype.Service;

@Service
public class ResponseHandler {
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

    public GetUserResponse handleGetUserResponse(GetUserEnums status, User user) {
        GetUserResponse getUserResponse = new GetUserResponse();
        switch (status) {
            case SUCCESS: {
                if (user == null) {
                    getUserResponse.setOperationCode(2001);
                    getUserResponse.setOperationMessage("USER NOT PRESENTED");
                    break;
                }
                getUserResponse.setOperationCode(0);
                getUserResponse.setOperationMessage("Success");

                UserInfo userInfo = new UserInfo();
                userInfo.setEmail(user.getEmail());
                userInfo.setUsername(user.getPassword());
                userInfo.setId(user.getId());
                userInfo.setPhone(user.getPhone());
                userInfo.setPosition(user.getPosition());

                getUserResponse.setUserInfo(userInfo);
                break;
            }
            case USER_NOT_FOUND: {
                getUserResponse.setOperationCode(1);
                getUserResponse.setOperationMessage("User not found");
                break;
            }
            default: {
                getUserResponse.setOperationCode(2000);
                getUserResponse.setOperationMessage("GENERAL ERROR");
            }
        }
        return getUserResponse;
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
                loginResponse.setToken(HashUtils.md5hash(user.getUsername() + user.getPassword()));
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
}
