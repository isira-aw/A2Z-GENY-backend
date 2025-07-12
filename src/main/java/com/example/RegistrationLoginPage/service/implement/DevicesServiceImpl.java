package com.example.RegistrationLoginPage.service.implement;

import com.example.RegistrationLoginPage.dto.DevicesDTO;
import com.example.RegistrationLoginPage.dto.LoginDTO;
import com.example.RegistrationLoginPage.dto.LoginResponse;
import com.example.RegistrationLoginPage.entity.Devices;
import com.example.RegistrationLoginPage.jwt.JwtService;
import com.example.RegistrationLoginPage.repository.DevicesRepository;
import com.example.RegistrationLoginPage.service.DevicesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevicesServiceImpl implements DevicesService {

    @Autowired
    private DevicesRepository devicesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public String registerDevice(DevicesDTO dto) {
        Devices device = new Devices();
        device.setDeviceUid(dto.getDeviceUid());
        device.setPassword(passwordEncoder.encode(dto.getPassword()));
        device.setApiKey(dto.getApiKey());
        device.setAuthDomain(dto.getAuthDomain());
        device.setDatabaseURL(dto.getDatabaseURL());
        device.setProjectId(dto.getProjectId());
        device.setStorageBucket(dto.getStorageBucket());
        device.setMessagingSenderId(dto.getMessagingSenderId());
        device.setAppId(dto.getAppId());
        device.setProject_password(dto.getProject_password());
        device.setTsx(dto.getTsx());
        devicesRepository.save(device);
        return "Device Registered Successfully!";
    }

    @Override
    public LoginResponse loginDevice(LoginDTO loginDTO) {
        Devices device = devicesRepository.findByDeviceUid(loginDTO.getDeviceUid())
                .orElseThrow(() -> new RuntimeException("Device not found"));
        if (!passwordEncoder.matches(loginDTO.getPassword(), device.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = jwtService.generateToken(device.getDeviceUid());
        return new LoginResponse(true, token, "Login Successful!");

    }

    @Override
    public List<Devices> getAllDevices() {
        return devicesRepository.findAll();
    }

    @Override
    public Devices getDeviceByUid(String uid) {
        return devicesRepository.findByDeviceUid(uid).orElse(null);
    }

    @Override
    public String extractDeviceUidFromToken(String token) {
        return jwtService.extractUsername(token); // returns deviceUid
    }
}
