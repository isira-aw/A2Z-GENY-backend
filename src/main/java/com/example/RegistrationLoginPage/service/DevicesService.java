package com.example.RegistrationLoginPage.service;


import com.example.RegistrationLoginPage.dto.DevicesDTO;
import com.example.RegistrationLoginPage.dto.LoginDTO;
import com.example.RegistrationLoginPage.dto.LoginResponse;
import com.example.RegistrationLoginPage.entity.Devices;

import java.util.List;

public interface  DevicesService {
    String registerDevice(DevicesDTO dto);
    LoginResponse loginDevice(LoginDTO loginDTO);
    List<Devices> getAllDevices();
    Devices getDeviceByUid(String uid);
    String extractDeviceUidFromToken(String token);
}