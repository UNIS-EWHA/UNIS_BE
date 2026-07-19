package com.ewha.unis.community.controller;

import com.ewha.unis.auth.security.CustomUserPrincipal;
import com.ewha.unis.community.dto.CommunityPostCreateRequest;
import com.ewha.unis.community.dto.CommunityPostCreateResponse;
import com.ewha.unis.community.dto.CommunityPostDetailResponse;
import com.ewha.unis.community.dto.CommunityPostListResponse;
import com.ewha.unis.community.dto.SaveToggleResponse;
import com.ewha.unis.community.entity.PostCategory;
import com.ewha.unis.community.service.CommunityPostService;
import com.ewha.unis.global.response.code.SuccessCode;
import com.ewha.unis.global.response.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/community/posts")
@RequiredArgsConstructor
public class CommunityPostController {
    private final CommunityPostService communityPostService;

    @GetMapping
    public BaseResponse<CommunityPostListResponse> getPosts(
            @RequestParam(required = false) PostCategory category,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        Pageable pageable = PageRequest.of(page, size);
        return BaseResponse.ok(communityPostService.getPosts(category, keyword, pageable, principal.memberId()));
    }

    @GetMapping("/{postId}")
    public BaseResponse<CommunityPostDetailResponse> getPostDetail(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return BaseResponse.ok(communityPostService.getPostDetail(postId, principal.memberId()));
    }

    @PostMapping
    public BaseResponse<CommunityPostCreateResponse> createPost(
            @Valid @RequestBody CommunityPostCreateRequest request,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        CommunityPostCreateResponse response = communityPostService.createPost(request, principal.memberId());
        return BaseResponse.of(SuccessCode.CREATED, response);
    }

    @PutMapping("/{postId}")
    public BaseResponse<Void> updatePost(
            @PathVariable Long postId,
            @Valid @RequestBody CommunityPostCreateRequest request,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        communityPostService.updatePost(postId, request, principal.memberId());
        return BaseResponse.ok();
    }

    @DeleteMapping("/{postId}")
    public BaseResponse<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        communityPostService.deletePost(postId, principal.memberId());
        return BaseResponse.ok();
    }

    @PostMapping("/{postId}/save")
    public BaseResponse<SaveToggleResponse> toggleSave(
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return BaseResponse.ok(communityPostService.toggleSave(postId, principal.memberId()));
    }
}
