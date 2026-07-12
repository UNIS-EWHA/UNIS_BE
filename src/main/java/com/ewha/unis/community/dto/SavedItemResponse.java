package com.ewha.unis.community.dto;

import com.ewha.unis.community.entity.CommunityPost;
import com.ewha.unis.community.entity.PostCategory;
import com.ewha.unis.community.entity.Recruitment;
import com.ewha.unis.community.entity.RecruitmentType;
import com.ewha.unis.community.entity.SavedItem;
import com.ewha.unis.community.entity.SavedTargetType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public record SavedItemResponse(
        Long savedId,
        SavedTargetType type,
        Long targetId,
        String title,
        PostCategory category,
        RecruitmentType recruitmentType,
        LocalDate deadline,
        Long dDay
) {
    public static SavedItemResponse ofPost(SavedItem savedItem, CommunityPost post) {
        return new SavedItemResponse(
                savedItem.getId(),
                SavedTargetType.POST,
                post.getId(),
                post.getTitle(),
                post.getCategory(),
                null,
                post.getDeadline(),
                calculateDDay(post.getDeadline())
        );
    }

    public static SavedItemResponse ofRecruitment(SavedItem savedItem, Recruitment recruitment) {
        return new SavedItemResponse(
                savedItem.getId(),
                SavedTargetType.RECRUITMENT,
                recruitment.getId(),
                recruitment.getTitle(),
                null,
                recruitment.getType(),
                recruitment.getDeadline(),
                calculateDDay(recruitment.getDeadline())
        );
    }

    private static Long calculateDDay(LocalDate deadline) {
        return deadline == null ? null : ChronoUnit.DAYS.between(LocalDate.now(), deadline);
    }
}
