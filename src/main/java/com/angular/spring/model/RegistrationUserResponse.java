package com.angular.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class RegistrationUserResponse {
    @JsonProperty("OperationCode")
    private Integer operationCode;

    @JsonProperty("OperationMessage")
    private String operationMessage;
}