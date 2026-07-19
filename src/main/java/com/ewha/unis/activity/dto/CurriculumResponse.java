package com.ewha.unis.activity.dto;

import com.ewha.unis.activity.entity.CurriculumStep;

public record CurriculumResponse(
        int order,
        String title,
        String description
) {
    public static CurriculumResponse from(CurriculumStep step) {
        return new CurriculumResponse(step.getStepOrder(), step.getTitle(), step.getDescription());
    }
}
