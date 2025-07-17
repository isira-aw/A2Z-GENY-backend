package com.example.RegistrationLoginPage.controller;

import com.example.RegistrationLoginPage.dto.CommonResponseDTO;
import com.example.RegistrationLoginPage.dto.UiSettingsDTO;
import com.example.RegistrationLoginPage.service.DevicesService;
import com.example.RegistrationLoginPage.service.UiService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ui")
@CrossOrigin
public class UiController {

    @Autowired
    private DevicesService devicesService;

    @Autowired
    private UiService uiService;

    @PostMapping("/settings")
    public ResponseEntity<CommonResponseDTO> addDeviceSettings(HttpServletRequest request, @RequestBody UiSettingsDTO dto) {
        String deviceUid = extractDeviceUid(request);
        if (deviceUid == null) {
            return ResponseEntity.status(401).body(new CommonResponseDTO(false, "Invalid or missing token"));
        }
        return ResponseEntity.ok(uiService.addSettings(deviceUid, dto));
    }

    @PutMapping("/settings")
    public ResponseEntity<CommonResponseDTO> updateDeviceSettings(HttpServletRequest request, @RequestBody UiSettingsDTO dto) {
        String deviceUid = extractDeviceUid(request);
        if (deviceUid == null) {
            return ResponseEntity.status(401).body(new CommonResponseDTO(false, "Invalid or missing token"));
        }
        return ResponseEntity.ok(uiService.updateSettings(deviceUid, dto));
    }

    @GetMapping("/settings")
    public ResponseEntity<CommonResponseDTO> getDeviceSettings(HttpServletRequest request) {
        String deviceUid = extractDeviceUid(request);
        if (deviceUid == null) {
            return ResponseEntity.status(401).body(new CommonResponseDTO(false, "Invalid or missing token"));
        }
        return ResponseEntity.ok(uiService.getSettings(deviceUid));
    }

    private String extractDeviceUid(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        String token = authHeader.substring(7);
        return devicesService.extractDeviceUidFromToken(token);
    }
}
