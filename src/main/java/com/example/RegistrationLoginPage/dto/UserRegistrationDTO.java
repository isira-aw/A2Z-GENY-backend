package com.example.RegistrationLoginPage.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {
    private String email;
    private String password;
    private String fullName;
}