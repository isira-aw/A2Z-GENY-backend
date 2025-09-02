package com.example.RegistrationLoginPage.controller;

import com.example.RegistrationLoginPage.dto.*;
import com.example.RegistrationLoginPage.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<CommonResponseDTO> registerUser(@RequestBody UserRegistrationDTO dto) {
        CommonResponseDTO response = userService.registerUser(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@RequestBody UserLoginDTO dto) {
        try {
            UserLoginResponse response = userService.loginUser(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            UserLoginResponse errorResponse = new UserLoginResponse(false, "Login failed: " + e.getMessage(), null);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @GetMapping("/devices")
    public ResponseEntity<CommonResponseDTO> getUserDevices(HttpServletRequest request) {
        String userEmail = extractUserEmailFromToken(request);
        if (userEmail == null) {
            return ResponseEntity.status(401).body(
                    new CommonResponseDTO(false, "Invalid token")
            );
        }

        CommonResponseDTO response = userService.getUserDevices(userEmail);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/devices/assign")
    public ResponseEntity<CommonResponseDTO> assignDevice(
            @RequestBody DeviceAssignmentDTO dto,
            HttpServletRequest request) {

        String userEmail = extractUserEmailFromToken(request);
        if (userEmail == null) {
            return ResponseEntity.status(401).body(
                    new CommonResponseDTO(false, "Invalid token")
            );
        }

        CommonResponseDTO response = userService.assignDeviceToUser(userEmail, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/devices/{deviceUid}")
    public ResponseEntity<CommonResponseDTO> removeDevice(
            @PathVariable String deviceUid,
            HttpServletRequest request) {

        String userEmail = extractUserEmailFromToken(request);
        if (userEmail == null) {
            return ResponseEntity.status(401).body(
                    new CommonResponseDTO(false, "Invalid token")
            );
        }

        CommonResponseDTO response = userService.removeDeviceFromUser(userEmail, deviceUid);
        return ResponseEntity.ok(response);
    }

    private String extractUserEmailFromToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String jwt = authHeader.substring(7);
        return userService.extractUserEmailFromToken(jwt);
    }
}