package com.ewha.unis.application.dto;

import java.time.LocalDateTime;

public record ApplicationCreateResponse(
        Long applicationId,
        LocalDateTime resultAnnounceAt
) {
}
