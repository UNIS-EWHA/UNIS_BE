package com.ewha.unis.about.dto;

import java.util.List;

public record AboutExperienceResponse(
        String sectionLabel,
        String title,
        List<ExperienceItemResponse> items
) {
}
