package com.example.RegistrationLoginPage.controller;

import com.example.RegistrationLoginPage.dto.DevicesDTO;
import com.example.RegistrationLoginPage.dto.LoginDTO;
import com.example.RegistrationLoginPage.dto.LoginResponse;
import com.example.RegistrationLoginPage.entity.Devices;
import com.example.RegistrationLoginPage.service.DevicesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
@CrossOrigin
public class DevicesController {

    @Autowired
    private DevicesService devicesService;

    @PostMapping("/signUp")
    public String register(@RequestBody DevicesDTO dto) {
        return devicesService.registerDevice(dto);
    }

    @PostMapping("/signIn")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {
        LoginResponse response = devicesService.loginDevice(loginDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public List<Devices> getAll() {
        return devicesService.getAllDevices();
    }
}
