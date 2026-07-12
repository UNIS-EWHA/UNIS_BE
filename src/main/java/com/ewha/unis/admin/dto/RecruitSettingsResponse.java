package com.ewha.unis.admin.dto;

import com.ewha.unis.admin.entity.RecruitSettings;
import com.ewha.unis.admin.entity.RecruitStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RecruitSettingsResponse(
        Integer generation,
        RecruitStatus status,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime resultAnnounceAt,
        Integer capacity,
        String schedule
) {
    public static RecruitSettingsResponse from(RecruitSettings settings) {
        return new RecruitSettingsResponse(
                settings.getGeneration(),
                settings.getStatus(),
                settings.getStartDate(),
                settings.getEndDate(),
                settings.getResultAnnounceAt(),
                settings.getCapacity(),
                settings.getSchedule()
        );
    }
}
