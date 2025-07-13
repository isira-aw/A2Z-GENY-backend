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

import java.util.List;

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
            LoginResponse errorResponse = new LoginResponse(false, "Login failed: " + e.getMessage(),null );
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/all")
    public List<Devices> getAll() {
        return devicesService.getAllDevices();
    }

    @PostMapping("/config")
    public ResponseEntity<CommonResponseDTO> getDeviceConfig(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(
                    new CommonResponseDTO(false, "Missing or invalid Authorization header")
            );
        }
        String jwt = authHeader.substring(7);
        String deviceUid = devicesService.extractDeviceUidFromToken(jwt);

        if (deviceUid == null) {
            return ResponseEntity.status(401).body(
                    new CommonResponseDTO(false, "Invalid token")
            );
        }
        Devices device = devicesService.getDeviceByUid(deviceUid);
        if (device == null) {
            return ResponseEntity.status(404).body(
                    new CommonResponseDTO(false, "Device not found")
            );
        }
        // Optional: Exclude password from the device info if needed
        device.setPassword(null);
        return ResponseEntity.ok(
                new CommonResponseDTO(true, "Device found", device)
        );
    }

}
