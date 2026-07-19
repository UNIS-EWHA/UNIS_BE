package com.ewha.unis.community.dto;

import com.ewha.unis.community.entity.CommunityPost;
import com.ewha.unis.community.entity.PostCategory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public record CommunityPostDetailResponse(
        Long postId,
        PostCategory category,
        String title,
        String content,
        String imageUrl,
        String organizer,
        LocalDate startDate,
        LocalDate endDate,
        List<PostTagDto> tags,
        String externalUrl,
        int viewCount,
        LocalDateTime createdAt,
        LocalDate deadline,
        Long dDay,
        boolean isSaved,
        boolean isOwner
) {
    public static CommunityPostDetailResponse of(CommunityPost post, boolean isSaved, boolean isOwner) {
        LocalDate deadline = post.getDeadline();
        Long dDay = deadline == null ? null : ChronoUnit.DAYS.between(LocalDate.now(), deadline);
        return new CommunityPostDetailResponse(
                post.getId(),
                post.getCategory(),
                post.getTitle(),
                post.getContent(),
                post.getImageUrl(),
                post.getOrganizer(),
                post.getStartDate(),
                post.getEndDate(),
                post.getTags().stream().map(PostTagDto::from).toList(),
                post.getExternalUrl(),
                post.getViewCount(),
                post.getCreatedAt(),
                deadline,
                dDay,
                isSaved,
                isOwner
        );
    }
}