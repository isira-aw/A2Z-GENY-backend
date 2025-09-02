package com.example.RegistrationLoginPage.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "devices")
public class Devices {

    @Id
    @Column(name = "device_uid", length = 255)
    private String deviceUid;

    @Column(name = "device_name", length = 255)
    private String deviceName;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    // Firebase configuration fields
    @Column(name = "apiKey", length = 255)
    private String apiKey;

    @Column(name = "authDomain", length = 255)
    private String authDomain;

    @Column(name = "databaseURL", length = 255)
    private String databaseURL;

    @Column(name = "projectId", length = 255)
    private String projectId;

    @Column(name = "storageBucket", length = 255)
    private String storageBucket;

    @Column(name = "messagingSenderId", length = 255)
    private String messagingSenderId;

    @Column(name = "appId", length = 255)
    private String appId;

    @Column(name = "project_password", length = 255)
    private String project_password;

    @Column(name = "tsx", length = 255)
    private String tsx;

    // Many devices belong to one user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @PreUpdate
    protected void onUpdate() {
        lastLogin = LocalDateTime.now();
    }
}