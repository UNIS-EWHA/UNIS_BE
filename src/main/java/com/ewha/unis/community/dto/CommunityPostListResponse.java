package com.ewha.unis.community.dto;

import java.util.List;

public record CommunityPostListResponse(
        List<CommunityPostSummaryResponse> posts,
        long totalCount,
        boolean hasNext
) {
}
