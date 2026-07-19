package com.ewha.unis.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record ExperienceItemDto(
        @NotBlank(message = "항목 제목은 필수입니다.")
        String title,
        @NotBlank(message = "항목 설명은 필수입니다.")
        String description
) {
}
