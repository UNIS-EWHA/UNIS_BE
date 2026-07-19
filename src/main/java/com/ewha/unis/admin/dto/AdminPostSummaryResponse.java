package com.ewha.unis.admin.dto;

import com.ewha.unis.admin.repository.AdminPostSummaryProjection;
import com.ewha.unis.community.entity.CommunityPost;
import com.ewha.unis.community.entity.Recruitment;
import com.ewha.unis.community.entity.SavedTargetType;

import java.time.LocalDateTime;

public record AdminPostSummaryResponse(
        Long targetId,
        SavedTargetType type,
        String author,
        String title,
        LocalDateTime createdAt,
        int viewCount
) {
    public static AdminPostSummaryResponse fromPost(CommunityPost post) {
        return new AdminPostSummaryResponse(post.getId(), SavedTargetType.POST, post.getAuthor().getName(),
                post.getTitle(), post.getCreatedAt(), post.getViewCount());
    }

    public static AdminPostSummaryResponse fromRecruitment(Recruitment recruitment) {
        return new AdminPostSummaryResponse(recruitment.getId(), SavedTargetType.RECRUITMENT, recruitment.getAuthor().getName(),
                recruitment.getTitle(), recruitment.getCreatedAt(), recruitment.getViewCount());
    }

    public static AdminPostSummaryResponse from(AdminPostSummaryProjection projection) {
        return new AdminPostSummaryResponse(projection.getTargetId(), SavedTargetType.valueOf(projection.getType()),
                projection.getAuthor(), projection.getTitle(), projection.getCreatedAt(), projection.getViewCount());
    }
}
