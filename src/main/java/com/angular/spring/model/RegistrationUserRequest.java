package com.angular.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RegistrationUserRequest {
    @JsonProperty("Email")
    private String email;

    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("Position")
    private String position;

    @JsonProperty("Phone")
    private String phone;
}