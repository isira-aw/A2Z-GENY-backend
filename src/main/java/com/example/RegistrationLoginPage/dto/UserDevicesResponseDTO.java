package com.example.RegistrationLoginPage.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDevicesResponseDTO {
    private Long userId;
    private String email;
    private String fullName;
    private List<DeviceDTO> devices;
}