package com.ewha.unis.admin.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PartsEditRequest(
        @NotEmpty(message = "파트는 최소 1개 이상이어야 합니다.")
        @Valid
        List<PartItemDto> parts
) {
}
