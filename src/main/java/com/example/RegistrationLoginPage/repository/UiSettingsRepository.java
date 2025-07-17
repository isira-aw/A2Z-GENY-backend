package com.example.RegistrationLoginPage.repository;

import com.example.RegistrationLoginPage.entity.UiSettingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UiSettingsRepository extends JpaRepository<UiSettingsEntity, Long> {
    Optional<UiSettingsEntity> findByDeviceUid(String deviceUid);
}
