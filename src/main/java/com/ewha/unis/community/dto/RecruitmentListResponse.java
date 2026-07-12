package com.ewha.unis.community.dto;

import java.util.List;

public record RecruitmentListResponse(
        List<RecruitmentSummaryResponse> recruitments,
        long totalCount,
        boolean hasNext
) {
}
