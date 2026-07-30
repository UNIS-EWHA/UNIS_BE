package com.ewha.unis.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PartItemDto(
        @NotBlank(message = "이름은 필수입니다.")
        String name,
        @NotBlank(message = "설명은 필수입니다.")
        String description,
        @NotNull(message = "태그 목록은 필수입니다.")
        List<String> tags
) {
}
