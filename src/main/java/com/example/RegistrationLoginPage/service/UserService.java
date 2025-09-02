package com.example.RegistrationLoginPage.service;

import com.example.RegistrationLoginPage.dto.*;
import com.example.RegistrationLoginPage.entity.Devices;
import com.example.RegistrationLoginPage.entity.User;
import com.example.RegistrationLoginPage.repository.DevicesRepository;
import com.example.RegistrationLoginPage.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DevicesRepository devicesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Key jwtKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public CommonResponseDTO registerUser(UserRegistrationDTO dto) {
        try {
            // Check if user already exists
            if (userRepository.existsByEmail(dto.getEmail())) {
                return new CommonResponseDTO(false, "Email already exists");
            }

            // Create new user
            User user = new User();
            user.setEmail(dto.getEmail());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setFullName(dto.getFullName());
            user.setIsActive(true);

            userRepository.save(user);

            return new CommonResponseDTO(true, "User registered successfully");
        } catch (Exception e) {
            return new CommonResponseDTO(false, "Registration failed: " + e.getMessage());
        }
    }

    public UserLoginResponse loginUser(UserLoginDTO dto) {
        try {
            Optional<User> userOpt = userRepository.findByEmailWithDevices(dto.getEmail());

            if (userOpt.isEmpty()) {
                return new UserLoginResponse(false, "User not found", null);
            }

            User user = userOpt.get();

            if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                return new UserLoginResponse(false, "Invalid password", null);
            }

            if (!user.getIsActive()) {
                return new UserLoginResponse(false, "Account is deactivated", null);
            }

            // Update last login
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            // Generate JWT token
            String token = generateJwtToken(user.getEmail(), user.getId());

            // Convert to response DTO
            UserResponseDTO userResponse = convertToUserResponseDTO(user);

            return new UserLoginResponse(true, "Login successful", token, userResponse);

        } catch (Exception e) {
            return new UserLoginResponse(false, "Login failed: " + e.getMessage(), null);
        }
    }

    public CommonResponseDTO assignDeviceToUser(String userEmail, DeviceAssignmentDTO dto) {
        try {
            Optional<User> userOpt = userRepository.findByEmail(userEmail);
            if (userOpt.isEmpty()) {
                return new CommonResponseDTO(false, "User not found");
            }

            Optional<Devices> deviceOpt = devicesRepository.findById(dto.getDeviceUid());
            if (deviceOpt.isEmpty()) {
                return new CommonResponseDTO(false, "Device not found");
            }

            Devices device = deviceOpt.get();
            device.setUser(userOpt.get());
            device.setDeviceName(dto.getDeviceName());
            devicesRepository.save(device);

            return new CommonResponseDTO(true, "Device assigned successfully");
        } catch (Exception e) {
            return new CommonResponseDTO(false, "Assignment failed: " + e.getMessage());
        }
    }

    public CommonResponseDTO getUserDevices(String userEmail) {
        try {
            Optional<User> userOpt = userRepository.findByEmailWithDevices(userEmail);
            if (userOpt.isEmpty()) {
                return new CommonResponseDTO(false, "User not found");
            }

            User user = userOpt.get();
            List<DeviceResponseDTO> devices = user.getDevices().stream()
                    .map(this::convertToDeviceResponseDTO)
                    .collect(Collectors.toList());

            return new CommonResponseDTO(true, "Devices retrieved successfully", devices);
        } catch (Exception e) {
            return new CommonResponseDTO(false, "Failed to get devices: " + e.getMessage());
        }
    }

    public CommonResponseDTO removeDeviceFromUser(String userEmail, String deviceUid) {
        try {
            Optional<User> userOpt = userRepository.findByEmail(userEmail);
            if (userOpt.isEmpty()) {
                return new CommonResponseDTO(false, "User not found");
            }

            Optional<Devices> deviceOpt = devicesRepository.findById(deviceUid);
            if (deviceOpt.isEmpty()) {
                return new CommonResponseDTO(false, "Device not found");
            }

            Devices device = deviceOpt.get();
            if (!device.getUser().equals(userOpt.get())) {
                return new CommonResponseDTO(false, "Device does not belong to this user");
            }

            device.setUser(null);
            devicesRepository.save(device);

            return new CommonResponseDTO(true, "Device removed successfully");
        } catch (Exception e) {
            return new CommonResponseDTO(false, "Removal failed: " + e.getMessage());
        }
    }

    public String extractUserEmailFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    private String generateJwtToken(String email, Long userId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                .signWith(jwtKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private UserResponseDTO convertToUserResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setLastLogin(user.getLastLogin());
        dto.setIsActive(user.getIsActive());

        if (user.getDevices() != null) {
            dto.setDevices(user.getDevices().stream()
                    .map(this::convertToDeviceResponseDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    private DeviceResponseDTO convertToDeviceResponseDTO(Devices device) {
        DeviceResponseDTO dto = new DeviceResponseDTO();
        dto.setDeviceUid(device.getDeviceUid());
        dto.setDeviceName(device.getDeviceName());
        dto.setLastLogin(device.getLastLogin());
        return dto;
    }
}