package com.ewha.unis.admin.dto;

import java.util.List;

public record AdminPostListResponse(
        List<AdminPostSummaryResponse> posts,
        long totalCount,
        boolean hasNext
) {
}
