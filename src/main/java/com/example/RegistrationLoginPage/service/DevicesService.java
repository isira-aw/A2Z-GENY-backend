package com.example.RegistrationLoginPage.service;

import com.example.RegistrationLoginPage.dto.DevicesDTO;
import com.example.RegistrationLoginPage.dto.LoginDTO;
import com.example.RegistrationLoginPage.dto.LoginResponse;
import com.example.RegistrationLoginPage.entity.Devices;
import com.example.RegistrationLoginPage.jwt.JwtService;
import com.example.RegistrationLoginPage.repository.DevicesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevicesService {

    @Autowired
    private DevicesRepository devicesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String registerDevice(DevicesDTO dto) {
        if (devicesRepository.findByDeviceUid(dto.getDeviceUid()).isPresent()) {
            return "Device ID already taken!";
        }

        Devices device = new Devices();
        device.setDeviceUid(dto.getDeviceUid());
        device.setOnerId(dto.getOnerId());
        device.setPassword(passwordEncoder.encode(dto.getPassword()));

        devicesRepository.save(device);
        return "Registration successful";
    }


    public LoginResponse loginDevice(LoginDTO loginDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getDeviceUid(), loginDTO.getPassword())
            );

            final String token = jwtService.generateToken(loginDTO.getDeviceUid());
            return new LoginResponse("Login successful", token);
        } catch (Exception e) {
            return new LoginResponse("Invalid device or password", null);
        }
    }

    public List<Devices> getAllDevices() {
        return devicesRepository.findAll();
    }
}
