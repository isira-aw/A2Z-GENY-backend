package com.example.RegistrationLoginPage.service.implement;

import com.example.RegistrationLoginPage.dto.CommonResponseDTO;
import com.example.RegistrationLoginPage.dto.UiSettingsDTO;
import com.example.RegistrationLoginPage.entity.UiSettingsEntity;
import com.example.RegistrationLoginPage.repository.UiSettingsRepository;
import com.example.RegistrationLoginPage.service.UiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UiServiceImpl implements UiService {

    @Autowired
    private UiSettingsRepository uiSettingsRepository;

    @Override
    public CommonResponseDTO addOrUpdateSettings(String deviceUid, UiSettingsDTO dto) {
        UiSettingsEntity settings = uiSettingsRepository.findByDeviceUid(deviceUid)
                .orElse(new UiSettingsEntity());

        settings.setDeviceUid(deviceUid); // Ensure UID is set

        // Always overwrite (optional: apply conditionally if needed)
        mapDtoToEntity(dto, settings);

        UiSettingsEntity saved = uiSettingsRepository.save(settings);
        return new CommonResponseDTO(true, "Settings saved successfully", mapEntityToDto(saved));
    }

    @Override
    public CommonResponseDTO getSettings(String deviceUid) {
        Optional<UiSettingsEntity> optionalSettings = uiSettingsRepository.findByDeviceUid(deviceUid);
        if (optionalSettings.isEmpty()) {
            return new CommonResponseDTO(false, "No settings found");
        }
        return new CommonResponseDTO(true, "Settings fetched successfully", mapEntityToDto(optionalSettings.get()));
    }

    private void mapDtoToEntity(UiSettingsDTO dto, UiSettingsEntity entity) {
        entity.setA1(dto.getA1());
        entity.setA2(dto.getA2());
        entity.setA3(dto.getA3());
        entity.setA4(dto.getA4());
        entity.setA5(dto.getA5());
        entity.setA6(dto.getA6());
        entity.setA7(dto.getA7());
        entity.setA8(dto.getA8());
        entity.setA9(dto.getA9());
        entity.setA10(dto.getA10());
        entity.setA11(dto.getA11());
        entity.setA12(dto.getA12());
        entity.setA13(dto.getA13());
        entity.setA14(dto.getA14());
        entity.setA15(dto.getA15());
    }

    @Override
    public CommonResponseDTO addSettings(String deviceUid, UiSettingsDTO dto) {
        if (uiSettingsRepository.findByDeviceUid(deviceUid).isPresent()) {
            return new CommonResponseDTO(false, "Settings already exist. Use update instead.");
        }

        UiSettingsEntity settings = new UiSettingsEntity();
        settings.setDeviceUid(deviceUid);
        mapDtoToEntity(dto, settings);

        UiSettingsEntity saved = uiSettingsRepository.save(settings);
        return new CommonResponseDTO(true, "Settings added successfully", mapEntityToDto(saved));
    }


    @Override
    public CommonResponseDTO updateSettings(String deviceUid, UiSettingsDTO dto) {
        Optional<UiSettingsEntity> optionalSettings = uiSettingsRepository.findByDeviceUid(deviceUid);

        if (optionalSettings.isEmpty()) {
            return new CommonResponseDTO(false, "Settings not found", null);
        }

        UiSettingsEntity settings = optionalSettings.get();

        if (dto.getA1() != null) settings.setA1(dto.getA1());
        if (dto.getA2() != null) settings.setA2(dto.getA2());
        if (dto.getA3() != null) settings.setA3(dto.getA3());
        if (dto.getA4() != null) settings.setA4(dto.getA4());
        if (dto.getA5() != null) settings.setA5(dto.getA5());
        if (dto.getA6() != null) settings.setA6(dto.getA6());
        if (dto.getA7() != null) settings.setA7(dto.getA7());
        if (dto.getA8() != null) settings.setA8(dto.getA8());
        if (dto.getA9() != null) settings.setA9(dto.getA9());
        if (dto.getA10() != null) settings.setA10(dto.getA10());
        if (dto.getA11() != null) settings.setA11(dto.getA11());
        if (dto.getA12() != null) settings.setA12(dto.getA12());
        if (dto.getA13() != null) settings.setA13(dto.getA13());
        if (dto.getA14() != null) settings.setA14(dto.getA14());
        if (dto.getA15() != null) settings.setA15(dto.getA15());

        UiSettingsEntity saved = uiSettingsRepository.save(settings);

        return new CommonResponseDTO(true, "Settings updated successfully", mapEntityToDto(saved));
    }


    private UiSettingsDTO mapEntityToDto(UiSettingsEntity entity) {
        UiSettingsDTO dto = new UiSettingsDTO();
        dto.setA1(entity.getA1());
        dto.setA2(entity.getA2());
        dto.setA3(entity.getA3());
        dto.setA4(entity.getA4());
        dto.setA5(entity.getA5());
        dto.setA6(entity.getA6());
        dto.setA7(entity.getA7());
        dto.setA8(entity.getA8());
        dto.setA9(entity.getA9());
        dto.setA10(entity.getA10());
        dto.setA11(entity.getA11());
        dto.setA12(entity.getA12());
        dto.setA13(entity.getA13());
        dto.setA14(entity.getA14());
        dto.setA15(entity.getA15());
        return dto;
    }
}
