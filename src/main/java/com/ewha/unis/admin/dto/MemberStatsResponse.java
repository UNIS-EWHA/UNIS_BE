package com.ewha.unis.admin.dto;

public record MemberStatsResponse(
        long total,
        long executive,
        long partLeader,
        long general
) {
}
