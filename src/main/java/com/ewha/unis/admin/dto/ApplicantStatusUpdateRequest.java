package com.ewha.unis.admin.dto;

import com.ewha.unis.application.entity.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public record ApplicantStatusUpdateRequest(
        @NotNull(message = "상태는 필수입니다.")
        ApplicationStatus status
) {
}
