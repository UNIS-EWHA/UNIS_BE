package com.ewha.unis.community.controller;

import com.ewha.unis.auth.security.CustomUserPrincipal;
import com.ewha.unis.community.dto.CommentCreateResponse;
import com.ewha.unis.community.dto.CommentRequest;
import com.ewha.unis.community.dto.CommentResponse;
import com.ewha.unis.community.service.CommentService;
import com.ewha.unis.global.response.code.SuccessCode;
import com.ewha.unis.global.response.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/community/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public BaseResponse<List<CommentResponse>> getComments(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return BaseResponse.ok(commentService.getComments(postId, principal.memberId()));
    }

    @PostMapping
    public BaseResponse<CommentCreateResponse> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        CommentCreateResponse response = commentService.createComment(postId, request, principal.memberId());
        return BaseResponse.of(SuccessCode.CREATED, response);
    }

    @PutMapping("/{commentId}")
    public BaseResponse<Void> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequest request,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        commentService.updateComment(postId, commentId, request, principal.memberId());
        return BaseResponse.ok();
    }

    @DeleteMapping("/{commentId}")
    public BaseResponse<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        commentService.deleteComment(postId, commentId, principal.memberId());
        return BaseResponse.ok();
    }
}
