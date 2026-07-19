package com.ewha.unis.community.dto;

import com.ewha.unis.community.entity.PostCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CommunityPostCreateRequest(
        @NotNull(message = "카테고리는 필수입니다.")
        PostCategory category,
        @NotBlank(message = "제목은 필수입니다.")
        String title,
        @NotBlank(message = "본문은 필수입니다.")
        String content,
        String organizer,
        LocalDate startDate,
        LocalDate endDate,
        @Valid
        List<PostTagDto> tags,
        String externalUrl,
        LocalDate deadline,
        String imageUrl
) {
}
