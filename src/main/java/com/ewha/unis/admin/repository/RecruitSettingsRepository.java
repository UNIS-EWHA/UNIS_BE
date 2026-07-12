package com.ewha.unis.admin.repository;

import com.ewha.unis.admin.entity.RecruitSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruitSettingsRepository extends JpaRepository<RecruitSettings, Long> {
    Optional<RecruitSettings> findTopByOrderByIdDesc();
}
