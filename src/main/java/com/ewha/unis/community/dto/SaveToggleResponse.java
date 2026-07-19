package com.ewha.unis.community.dto;

public record SaveToggleResponse(
        boolean isSaved
) {
    public static SaveToggleResponse of(boolean isSaved) {
        return new SaveToggleResponse(isSaved);
    }
}
