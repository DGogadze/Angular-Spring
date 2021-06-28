package com.angular.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;

@Data
public class RegistrationUserResponse {
    @JsonProperty("OperationCode")
    private Integer operationCode;

    @JsonProperty("OperationMessage")
    private String operationMessage;
}