package com.angular.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthenticationRequest {
    @JsonProperty("Username")
    private String username;
}
