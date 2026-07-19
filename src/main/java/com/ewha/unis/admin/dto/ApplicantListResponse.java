package com.ewha.unis.admin.dto;

import java.util.List;

public record ApplicantListResponse(
        List<ApplicantSummaryResponse> applicants,
        long totalCount,
        boolean hasNext
) {
}
