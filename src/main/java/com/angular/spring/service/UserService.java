package com.angular.spring.service;

import com.angular.spring.entities.User;
import com.angular.spring.model.*;
import com.angular.spring.repository.UserRepository;
import com.angular.spring.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Optional<User> optionalUser = userRepository.findByUsername(username);
        GetUserResponse getUserResponse = new GetUserResponse();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Long id = user.getId();
            String email = user.getEmail();

            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(email);
            userInfo.setUsername(username);
            userInfo.setId(id);

            getUserResponse.setOperationCode(0);
            getUserResponse.setOperationMessage("Success");
            getUserResponse.setUserInfo(userInfo);
            LOG.info("User request -> " + JsonUtils.parse(getUserRequest) + " => " + JsonUtils.parse(getUserResponse));
        } else {
            getUserResponse.setOperationCode(1);
            getUserResponse.setOperationMessage("User not found");
            LOG.info("User request failed -> " + JsonUtils.parse(getUserRequest) + " => " + JsonUtils.parse(getUserResponse));
        }
        return getUserResponse;
    }

    public RegistrationUserResponse registration(RegistrationUserRequest registrationUserRequest){
        String username = registrationUserRequest.getUsername();
        String email = registrationUserRequest.getEmail();
        String password = registrationUserRequest.getPassword();

        RegistrationUserResponse registrationUserResponse = new RegistrationUserResponse();
        if (userRepository.findByUsername(username).isPresent() || userRepository.findByEmail(email).isPresent()){
            registrationUserResponse.setOperationCode(2);
            registrationUserResponse.setOperationMessage("User with this username or email exist");
            LOG.info("User registration failed -> " + JsonUtils.parse(registrationUserRequest) + " => " + JsonUtils.parse(registrationUserResponse));
        } else {
            registrationUserResponse.setOperationCode(0);
            registrationUserResponse.setOperationMessage("Success");

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            userRepository.save(user);
            LOG.info("User registration -> " + JsonUtils.parse(registrationUserRequest));
        }
        return registrationUserResponse;
    }
}
