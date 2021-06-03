package com.angular.spring.service;

import com.angular.spring.entities.User;
import com.angular.spring.model.*;
import com.angular.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
        } else {
            getUserResponse.setOperationCode(1);
            getUserResponse.setOperationMessage("User not found");
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
            registrationUserResponse.setOperationMessage("User with this username or email is exist");
        } else {
            registrationUserResponse.setOperationCode(0);
            registrationUserResponse.setOperationMessage("Success");

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            userRepository.save(user);
        }
        return registrationUserResponse;
    }
}
