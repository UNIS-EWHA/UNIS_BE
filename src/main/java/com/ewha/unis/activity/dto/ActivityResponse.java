package com.ewha.unis.activity.dto;

import java.util.List;

public record ActivityResponse(
        List<ProgramResponse> programs,
        List<CurriculumResponse> curriculum
) {
}
