package com.angular.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class LoginResponse {
    @JsonProperty("OperationCode")
    private Integer operationCode;

    @JsonProperty("OperationMessage")
    private String operationMessage;

    @JsonProperty("Token")
    private String token;

    @JsonProperty("Username")
    private String username;
}
