package com.ewha.unis.admin.dto;

public record ApplicantStatsResponse(
        long total,
        long currentGeneration,
        long waiting,
        long reviewing,
        long passed,
        long failed
) {
}
