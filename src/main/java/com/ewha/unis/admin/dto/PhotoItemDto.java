package com.ewha.unis.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record PhotoItemDto(
        @NotBlank(message = "라벨은 필수입니다.")
        String label,
        @NotBlank(message = "이미지 URL은 필수입니다.")
        String imageUrl
) {
}
