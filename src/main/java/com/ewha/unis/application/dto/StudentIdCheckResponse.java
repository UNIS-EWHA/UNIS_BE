package com.ewha.unis.application.dto;

public record StudentIdCheckResponse(
        boolean available
) {
    public static StudentIdCheckResponse of(boolean available) {
        return new StudentIdCheckResponse(available);
    }
}
