package com.angular.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class UserInfo {
    @JsonProperty("Id")
    private Long id;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("Username")
    private String username;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Position")
    private String position;
}