package com.example.RegistrationLoginPage.repository;

import com.example.RegistrationLoginPage.entity.Devices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DevicesRepository extends JpaRepository<Devices, String> {
    Optional<Devices> findByDeviceUid(String deviceUid);
}