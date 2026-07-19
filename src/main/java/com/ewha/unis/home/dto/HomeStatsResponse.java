package com.ewha.unis.home.dto;

public record HomeStatsResponse(
        Integer generation,
        long projectCount,
        long memberCount,
        int awardCount
) {
}
