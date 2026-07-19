package com.ewha.unis.application.dto;

import com.ewha.unis.admin.entity.RecruitSettings;
import com.ewha.unis.admin.entity.RecruitStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RecruitInfoResponse(
        Integer generation,
        RecruitStatus status,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime resultAnnounceAt
) {
    public static RecruitInfoResponse from(RecruitSettings settings) {
        return new RecruitInfoResponse(
                settings.getGeneration(),
                settings.getStatus(),
                settings.getStartDate(),
                settings.getEndDate(),
                settings.getResultAnnounceAt()
        );
    }
}
