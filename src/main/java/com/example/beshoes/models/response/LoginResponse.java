package com.example.beshoes.models.response;

import lombok.Data;

@Data
public class LoginResponse {

    private String accessToken;
    private String tokenType = "Bearer ";

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
