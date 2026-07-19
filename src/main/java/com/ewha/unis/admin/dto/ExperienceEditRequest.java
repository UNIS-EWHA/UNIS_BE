package com.ewha.unis.admin.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ExperienceEditRequest(
        @NotBlank(message = "섹션 레이블은 필수입니다.")
        String sectionLabel,
        @NotBlank(message = "제목은 필수입니다.")
        String title,
        @NotEmpty(message = "항목은 최소 1개 이상이어야 합니다.")
        @Valid
        List<ExperienceItemDto> items
) {
}
