package com.example.RegistrationLoginPage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ui_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UiSettingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_uid", unique = true, nullable = false)
    private String deviceUid;

    private String A1;
    private String A2;
    private String A3;
    private String A4;
    private String A5;
    private String A6;
    private String A7;
    private String A8;
    private String A9;
    private String A10;
    private String A11;
    private String A12;
    private String A13;
    private String A14;
    private String A15;
}
