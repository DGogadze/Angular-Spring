package com.angular.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
public class AuthenticationResponse {
    @JsonProperty("Authenticated")
    private boolean isAuthenticated;
}
