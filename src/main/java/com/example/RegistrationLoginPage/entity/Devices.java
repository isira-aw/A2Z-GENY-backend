package com.example.RegistrationLoginPage.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "devices")
public class Devices {

    @Id
    @Column(name = "device_uid", length = 255)
    private String deviceUid;

    @Column(name = "oner_id", length = 255)
    private String onerId;

    @Column(name = "password", length = 255)
    private String password;

    public String getDeviceUid() {
        return deviceUid;
    }

    public void setDeviceUid(String deviceUid) {
        this.deviceUid = deviceUid;
    }

    public String getOnerId() {
        return onerId;
    }

    public void setOnerId(String onerId) {
        this.onerId = onerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
