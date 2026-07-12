package com.ewha.unis.community.controller;

import com.ewha.unis.auth.security.CustomUserPrincipal;
import com.ewha.unis.community.dto.RecruitmentCreateRequest;
import com.ewha.unis.community.dto.RecruitmentCreateResponse;
import com.ewha.unis.community.dto.RecruitmentDetailResponse;
import com.ewha.unis.community.dto.RecruitmentListResponse;
import com.ewha.unis.community.dto.SaveToggleResponse;
import com.ewha.unis.community.service.RecruitmentService;
import com.ewha.unis.global.response.code.SuccessCode;
import com.ewha.unis.global.response.dto.BaseResponse;
import com.ewha.unis.member.domain.MemberPart;
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
@RequestMapping("/api/v1/community/recruitments")
@RequiredArgsConstructor
public class RecruitmentController {
    private final RecruitmentService recruitmentService;

    @GetMapping
    public BaseResponse<RecruitmentListResponse> getRecruitments(
            @RequestParam(required = false) MemberPart part,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return BaseResponse.ok(recruitmentService.getRecruitments(part, keyword, pageable));
    }

    @GetMapping("/{recruitmentId}")
    public BaseResponse<RecruitmentDetailResponse> getRecruitmentDetail(
            @PathVariable Long recruitmentId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return BaseResponse.ok(recruitmentService.getRecruitmentDetail(recruitmentId, principal.memberId()));
    }

    @PostMapping
    public BaseResponse<RecruitmentCreateResponse> createRecruitment(
            @Valid @RequestBody RecruitmentCreateRequest request,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        RecruitmentCreateResponse response = recruitmentService.createRecruitment(request, principal.memberId());
        return BaseResponse.of(SuccessCode.CREATED, response);
    }

    @PutMapping("/{recruitmentId}")
    public BaseResponse<Void> updateRecruitment(
            @PathVariable Long recruitmentId,
            @Valid @RequestBody RecruitmentCreateRequest request,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        recruitmentService.updateRecruitment(recruitmentId, request, principal.memberId());
        return BaseResponse.ok();
    }

    @DeleteMapping("/{recruitmentId}")
    public BaseResponse<Void> deleteRecruitment(
            @PathVariable Long recruitmentId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        recruitmentService.deleteRecruitment(recruitmentId, principal.memberId());
        return BaseResponse.ok();
    }

    @PostMapping("/{recruitmentId}/save")
    public BaseResponse<SaveToggleResponse> toggleSave(
            @PathVariable Long recruitmentId,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        return BaseResponse.ok(recruitmentService.toggleSave(recruitmentId, principal.memberId()));
    }
}
