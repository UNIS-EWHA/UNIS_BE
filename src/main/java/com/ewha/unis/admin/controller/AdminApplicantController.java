package com.ewha.unis.admin.controller;

import com.ewha.unis.admin.dto.ApplicantDetailResponse;
import com.ewha.unis.admin.dto.ApplicantListResponse;
import com.ewha.unis.admin.dto.ApplicantStatsResponse;
import com.ewha.unis.admin.dto.ApplicantStatusUpdateRequest;
import com.ewha.unis.admin.service.AdminApplicantService;
import com.ewha.unis.application.entity.ApplicationStatus;
import com.ewha.unis.global.response.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/applicants")
@RequiredArgsConstructor
public class AdminApplicantController {
    private final AdminApplicantService adminApplicantService;

    @GetMapping("/stats")
    public BaseResponse<ApplicantStatsResponse> getStats() {
        return BaseResponse.ok(adminApplicantService.getStats());
    }

    @GetMapping
    public BaseResponse<ApplicantListResponse> getApplicants(
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return BaseResponse.ok(adminApplicantService.getApplicants(status, pageable));
    }

    @GetMapping("/{applicantId}")
    public BaseResponse<ApplicantDetailResponse> getApplicantDetail(@PathVariable Long applicantId) {
        return BaseResponse.ok(adminApplicantService.getApplicantDetail(applicantId));
    }

    @PatchMapping("/{applicantId}/status")
    public BaseResponse<Void> updateStatus(
            @PathVariable Long applicantId,
            @Valid @RequestBody ApplicantStatusUpdateRequest request) {
        adminApplicantService.updateStatus(applicantId, request);
        return BaseResponse.ok();
    }
}
