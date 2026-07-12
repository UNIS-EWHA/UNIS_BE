package com.ewha.unis.about.dto;

import com.ewha.unis.about.entity.CoreValue;

public record CoreValueResponse(
        String title,
        String description
) {
    public static CoreValueResponse from(CoreValue coreValue) {
        return new CoreValueResponse(coreValue.getTitle(), coreValue.getDescription());
    }
}
