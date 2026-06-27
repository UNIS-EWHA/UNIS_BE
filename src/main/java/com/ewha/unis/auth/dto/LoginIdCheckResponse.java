package com.ewha.unis.auth.dto;

public record LoginIdCheckResponse(
        boolean available
) {
    public static LoginIdCheckResponse of(boolean available) {
        return new LoginIdCheckResponse(available);
    }
}
