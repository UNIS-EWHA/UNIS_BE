package com.ewha.unis.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record FaqRequest(
        @NotBlank(message = "질문은 필수입니다.")
        String question,
        @NotBlank(message = "답변은 필수입니다.")
        String answer
) {
}
