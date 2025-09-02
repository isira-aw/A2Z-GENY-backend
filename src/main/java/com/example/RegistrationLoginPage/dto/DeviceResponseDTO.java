package com.example.RegistrationLoginPage.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeviceResponseDTO {
    private String deviceUid;
    private String deviceName;
    private LocalDateTime lastLogin;
}