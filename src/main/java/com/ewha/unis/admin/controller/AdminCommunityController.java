package com.ewha.unis.admin.controller;

import com.ewha.unis.admin.dto.AdminPostListResponse;
import com.ewha.unis.admin.service.AdminCommunityService;
import com.ewha.unis.community.entity.SavedTargetType;
import com.ewha.unis.global.response.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/community")
@RequiredArgsConstructor
public class AdminCommunityController {
    private final AdminCommunityService adminCommunityService;

    @GetMapping("/posts")
    public BaseResponse<AdminPostListResponse> getPosts(
            @RequestParam(required = false) SavedTargetType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return BaseResponse.ok(adminCommunityService.getPosts(type, pageable));
    }

    @DeleteMapping("/posts/{postId}")
    public BaseResponse<Void> deletePost(@PathVariable Long postId) {
        adminCommunityService.deletePost(postId);
        return BaseResponse.ok();
    }

    @DeleteMapping("/recruitments/{recruitmentId}")
    public BaseResponse<Void> deleteRecruitment(@PathVariable Long recruitmentId) {
        adminCommunityService.deleteRecruitment(recruitmentId);
        return BaseResponse.ok();
    }
}
