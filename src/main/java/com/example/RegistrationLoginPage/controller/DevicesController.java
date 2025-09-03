package com.example.RegistrationLoginPage.controller;

import com.example.RegistrationLoginPage.dto.CommonResponseDTO;
import com.example.RegistrationLoginPage.dto.DevicesDTO;
import com.example.RegistrationLoginPage.dto.LoginDTO;
import com.example.RegistrationLoginPage.dto.LoginResponse;
import com.example.RegistrationLoginPage.entity.Devices;
import com.example.RegistrationLoginPage.service.DevicesService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/devices")
@CrossOrigin
public class DevicesController {

    @Autowired
    private DevicesService devicesService;

    @PostMapping("/signUp")
    public ResponseEntity<CommonResponseDTO> register(@RequestBody DevicesDTO dto) {
        CommonResponseDTO response = devicesService.registerDevice(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signIn")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {
        try {
            LoginResponse response = devicesService.loginDevice(loginDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LoginResponse errorResponse = new LoginResponse(false, "Login failed: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/all")
    public List<Devices> getAll() {
        return devicesService.getAllDevices();
    }

    @PostMapping("/config")
    public ResponseEntity<Map<String, Object>> getDeviceConfig(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("message", "Missing or invalid Authorization header");
            return ResponseEntity.badRequest().body(errorResponse);
        }

        String jwt = authHeader.substring(7);
        String deviceUid = devicesService.extractDeviceUidFromToken(jwt);

        if (deviceUid == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("message", "Invalid token");
            return ResponseEntity.status(401).body(errorResponse);
        }

        Devices device = devicesService.getDeviceByUid(deviceUid);
        if (device == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", false);
            errorResponse.put("message", "Device not found");
            return ResponseEntity.status(404).body(errorResponse);
        }

        // Create a clean response with only needed fields - no circular references
        Map<String, Object> deviceConfig = new HashMap<>();
        deviceConfig.put("deviceUid", device.getDeviceUid());
        deviceConfig.put("deviceName", device.getDeviceName());
        deviceConfig.put("lastLogin", device.getLastLogin());

        // Firebase configuration
        deviceConfig.put("apiKey", device.getApiKey());
        deviceConfig.put("authDomain", device.getAuthDomain());
        deviceConfig.put("databaseURL", device.getDatabaseURL());
        deviceConfig.put("projectId", device.getProjectId());
        deviceConfig.put("storageBucket", device.getStorageBucket());
        deviceConfig.put("messagingSenderId", device.getMessagingSenderId());
        deviceConfig.put("appId", device.getAppId());

        // Additional configuration
        if (device.getProject_password() != null) {
            deviceConfig.put("project_password", device.getProject_password());
        }
        if (device.getTsx() != null) {
            deviceConfig.put("tsx", device.getTsx());
        }

        // Explicitly exclude password and user object to prevent circular references
        // Do not include: device.getPassword(), device.getUser()

        Map<String, Object> response = new HashMap<>();
        response.put("status", true);
        response.put("message", "Device config retrieved successfully");
        response.put("data", deviceConfig);

        return ResponseEntity.ok(response);
    }
}