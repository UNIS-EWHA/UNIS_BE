package com.ewha.unis.admin.dto;

import com.ewha.unis.admin.entity.RecruitStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record RecruitSettingsRequest(
        @NotNull(message = "기수는 필수입니다.")
        Integer generation,
        @NotNull(message = "모집 상태는 필수입니다.")
        RecruitStatus status,
        @NotNull(message = "지원 시작일은 필수입니다.")
        LocalDate startDate,
        @NotNull(message = "지원 마감일은 필수입니다.")
        LocalDate endDate,
        LocalDateTime resultAnnounceAt,
        @NotNull(message = "모집 인원은 필수입니다.")
        Integer capacity,
        @NotBlank(message = "활동 요일·시간은 필수입니다.")
        String schedule
) {
}
