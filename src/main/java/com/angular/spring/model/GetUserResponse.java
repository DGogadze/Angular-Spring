package com.angular.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserResponse {
    @JsonProperty("OperationCode")
    private Integer operationCode;

    @JsonProperty("OperationMessage")
    private String operationMessage;

    @JsonProperty("Token")
    private String token;

    @JsonProperty("UserInfo")
    private UserInfo userInfo;
}
