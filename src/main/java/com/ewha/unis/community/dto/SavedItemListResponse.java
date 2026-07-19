package com.ewha.unis.community.dto;

import java.util.List;

public record SavedItemListResponse(
        List<SavedItemResponse> saved,
        long totalCount,
        boolean hasNext
) {
}
