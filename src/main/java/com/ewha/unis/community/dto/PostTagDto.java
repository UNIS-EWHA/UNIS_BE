package com.ewha.unis.community.dto;

import com.ewha.unis.community.entity.PostTag;
import jakarta.validation.constraints.NotBlank;

public record PostTagDto(
        @NotBlank(message = "태그 라벨은 필수입니다.")
        String label,
        @NotBlank(message = "태그 내용은 필수입니다.")
        String content
) {
    public static PostTagDto from(PostTag postTag) {
        return new PostTagDto(postTag.getLabel(), postTag.getContent());
    }
}
