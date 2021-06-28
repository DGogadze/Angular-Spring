package com.angular.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class LoginRequest {
    @JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;
}
