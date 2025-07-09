package com.example.RegistrationLoginPage.service.implement;

import com.example.RegistrationLoginPage.entity.Devices;
import com.example.RegistrationLoginPage.repository.DevicesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private DevicesRepository devicesRepository;

    @Override
    public UserDetails loadUserByUsername(String deviceUid) throws UsernameNotFoundException {
        Devices device = devicesRepository.findByDeviceUid(deviceUid)
                .orElseThrow(() -> new UsernameNotFoundException("Device not found: " + deviceUid));

        return new User(device.getDeviceUid(), device.getPassword(), Collections.emptyList());
    }
}
