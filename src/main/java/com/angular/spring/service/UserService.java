package com.angular.spring.service;

import com.angular.spring.entities.User;
import com.angular.spring.model.*;
import com.angular.spring.repository.UserRepository;
import com.angular.spring.utils.HashUtils;
import com.angular.spring.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    private final Logger LOG = LoggerFactory.getLogger(UserService.class);

    public GetUserResponse findUser(GetUserRequest getUserRequest) {
        String username = getUserRequest.getUsername();
        String password = getUserRequest.getPassword();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        GetUserResponse getUserResponse = new GetUserResponse();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (HashUtils.md5hash(password).equals(user.getPassword())) {
                Long id = user.getId();
                String email = user.getEmail();
                String phone = user.getPhone();
                String position = user.getPosition();

                UserInfo userInfo = new UserInfo();
                userInfo.setEmail(email);
                userInfo.setUsername(username);
                userInfo.setId(id);
                userInfo.setPhone(phone);
                userInfo.setPosition(position);

                getUserResponse.setOperationCode(0);
                getUserResponse.setOperationMessage("Success");
                getUserResponse.setUserInfo(userInfo);
            } else {
                getUserResponse.setOperationCode(3);
                getUserResponse.setOperationMessage("Invalid credentials!");
            }
            LOG.info("User request -> " + JsonUtils.parse(getUserRequest) + " => " + JsonUtils.parse(getUserResponse));
        } else {
            getUserResponse.setOperationCode(1);
            getUserResponse.setOperationMessage("User not found");
            LOG.info("User request failed -> " + JsonUtils.parse(getUserRequest) + " => " + JsonUtils.parse(getUserResponse));
        }
        return getUserResponse;
    }

    public RegistrationUserResponse registration(RegistrationUserRequest registrationUserRequest) {
        String username = registrationUserRequest.getUsername();
        String email = registrationUserRequest.getEmail();
        String password = registrationUserRequest.getPassword();
        String phone = registrationUserRequest.getPhone();
        String position = registrationUserRequest.getPosition();

        RegistrationUserResponse registrationUserResponse = new RegistrationUserResponse();
        if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()) {
            registrationUserResponse.setOperationCode(2);
            registrationUserResponse.setOperationMessage("User with this username or email exist");
            LOG.info("User registration failed -> " + JsonUtils.parse(registrationUserRequest) + " => " + JsonUtils.parse(registrationUserResponse));
        } else {
            registrationUserResponse.setOperationCode(0);
            registrationUserResponse.setOperationMessage("Success");

            User user = new User();
            user.setUsername(username);
            user.setPassword(HashUtils.md5hash(password));
            user.setEmail(email);
            user.setPhone(phone);
            user.setPosition(position);
            userRepository.save(user);
            LOG.info("User registration -> " + JsonUtils.parse(registrationUserRequest));
        }
        return registrationUserResponse;
    }
}
