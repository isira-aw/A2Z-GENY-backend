package com.example.RegistrationLoginPage.dto;

import lombok.Data;

@Data
public class UserLoginResponse {
    private boolean success;
    private String message;
    private String token;
    private UserResponseDTO user;

    public UserLoginResponse(boolean success, String message, String token) {
        this.success = success;
        this.message = message;
        this.token = token;
    }

    public UserLoginResponse(boolean success, String message, String token, UserResponseDTO user) {
        this.success = success;
        this.message = message;
        this.token = token;
        this.user = user;
    }
}
