package com.example.RegistrationLoginPage.service;

import com.example.RegistrationLoginPage.dto.CommonResponseDTO;
import com.example.RegistrationLoginPage.dto.UiSettingsDTO;

public interface UiService {
    CommonResponseDTO addOrUpdateSettings(String deviceUid, UiSettingsDTO dto);
    CommonResponseDTO addSettings(String deviceUid, UiSettingsDTO dto);
    CommonResponseDTO updateSettings(String deviceUid, UiSettingsDTO dto);
    CommonResponseDTO getSettings(String deviceUid);
}
