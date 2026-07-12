package com.ewha.unis.community.dto;

import com.ewha.unis.community.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long commentId,
        String author,
        String content,
        LocalDateTime createdAt,
        boolean isOwner
) {
    public static CommentResponse of(Comment comment, boolean isOwner) {
        return new CommentResponse(
                comment.getId(),
                comment.getAuthor().getName(),
                comment.getContent(),
                comment.getCreatedAt(),
                isOwner
        );
    }
}
