package com.ewha.unis.admin.dto;

import java.util.List;

public record MemberListResponse(
        List<MemberSummaryResponse> members,
        long totalCount,
        boolean hasNext
) {
}
