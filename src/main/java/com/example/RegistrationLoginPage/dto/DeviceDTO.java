package com.example.RegistrationLoginPage.dto;

import lombok.Data;

@Data
public class DeviceDTO {
    private String deviceUid;
    private String deviceName;
    private String ownerId;
    private String password;
    private String lastLogin;
}