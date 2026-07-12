package com.ewha.unis.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record AboutIntroEditRequest(
        @NotBlank(message = "섹션 레이블은 필수입니다.")
        String sectionLabel,
        @NotBlank(message = "제목은 필수입니다.")
        String title,
        @NotBlank(message = "본문은 필수입니다.")
        String body
) {
}
