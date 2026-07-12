package com.ewha.unis.community.dto;

import com.ewha.unis.community.entity.CommunityPost;
import com.ewha.unis.community.entity.PostCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record CommunityPostSummaryResponse(
        Long postId,
        PostCategory category,
        String title,
        String content,
        String organizer,
        int viewCount,
        LocalDateTime createdAt,
        LocalDate deadline,
        Long dDay,
        boolean isSaved
) {
    public static CommunityPostSummaryResponse of(CommunityPost post, boolean isSaved) {
        LocalDate deadline = post.getDeadline();
        Long dDay = deadline == null ? null : ChronoUnit.DAYS.between(LocalDate.now(), deadline);
        return new CommunityPostSummaryResponse(
                post.getId(),
                post.getCategory(),
                post.getTitle(),
                post.getContent(),
                post.getOrganizer(),
                post.getViewCount(),
                post.getCreatedAt(),
                deadline,
                dDay,
                isSaved
        );
    }
}
