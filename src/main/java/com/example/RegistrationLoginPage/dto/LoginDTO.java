package com.example.RegistrationLoginPage.dto;

public class LoginDTO {
    private String deviceUid;
    private String password;


    public String getDeviceUid() {
        return deviceUid;
    }

    public void setDeviceUid(String deviceUid) {
        this.deviceUid = deviceUid;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

}
