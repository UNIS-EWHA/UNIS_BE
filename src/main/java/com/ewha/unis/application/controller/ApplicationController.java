package com.ewha.unis.application.controller;

import com.ewha.unis.application.dto.ApplicationCreateRequest;
import com.ewha.unis.application.dto.ApplicationCreateResponse;
import com.ewha.unis.application.dto.PortfolioUploadResponse;
import com.ewha.unis.application.dto.RecruitInfoResponse;
import com.ewha.unis.application.dto.StudentIdCheckResponse;
import com.ewha.unis.application.service.ApplicationService;
import com.ewha.unis.application.service.PortfolioStorageService;
import com.ewha.unis.auth.security.CustomUserPrincipal;
import com.ewha.unis.global.response.code.SuccessCode;
import com.ewha.unis.global.response.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;
    private final PortfolioStorageService portfolioStorageService;

    @GetMapping("/recruit-info")
    public BaseResponse<RecruitInfoResponse> getRecruitInfo() {
        return BaseResponse.ok(applicationService.getRecruitInfo());
    }

    @GetMapping("/student-id/check")
    public BaseResponse<StudentIdCheckResponse> checkStudentId(@RequestParam String studentId) {
        return BaseResponse.ok(applicationService.checkStudentId(studentId));
    }

    @PostMapping(value = "/portfolio/upload", consumes = "multipart/form-data")
    public BaseResponse<PortfolioUploadResponse> uploadPortfolio(@RequestPart("file") MultipartFile file) {
        return BaseResponse.ok(portfolioStorageService.store(file));
    }

    @PostMapping
    public BaseResponse<ApplicationCreateResponse> submit(
            @Valid @RequestBody ApplicationCreateRequest request,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        ApplicationCreateResponse response = applicationService.submit(request, principal.memberId());
        return BaseResponse.of(SuccessCode.CREATED, response);
    }
}
