package com.ewha.unis.admin.service;

import com.ewha.unis.admin.dto.ApplicantDetailResponse;
import com.ewha.unis.admin.dto.ApplicantListResponse;
import com.ewha.unis.admin.dto.ApplicantStatsResponse;
import com.ewha.unis.admin.dto.ApplicantStatusUpdateRequest;
import com.ewha.unis.admin.dto.ApplicantSummaryResponse;
import com.ewha.unis.admin.entity.RecruitSettings;
import com.ewha.unis.admin.repository.RecruitSettingsRepository;
import com.ewha.unis.application.entity.Application;
import com.ewha.unis.application.entity.ApplicationStatus;
import com.ewha.unis.application.repository.ApplicationRepository;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminApplicantService {
    private final ApplicationRepository applicationRepository;
    private final RecruitSettingsRepository recruitSettingsRepository;

    public ApplicantStatsResponse getStats() {
        Integer currentGeneration = recruitSettingsRepository.findTopByOrderByIdDesc()
                .map(RecruitSettings::getGeneration)
                .orElse(null);

        long total = applicationRepository.count();
        long currentGenerationCount = currentGeneration == null ? 0
                : applicationRepository.countByGeneration(currentGeneration);
        long waiting = currentGeneration == null ? 0
                : applicationRepository.countByGenerationAndStatus(currentGeneration, ApplicationStatus.WAITING);
        long reviewing = currentGeneration == null ? 0
                : applicationRepository.countByGenerationAndStatus(currentGeneration, ApplicationStatus.REVIEWING);
        long passed = currentGeneration == null ? 0
                : applicationRepository.countByGenerationAndStatus(currentGeneration, ApplicationStatus.PASSED);
        long failed = currentGeneration == null ? 0
                : applicationRepository.countByGenerationAndStatus(currentGeneration, ApplicationStatus.FAILED);

        return new ApplicantStatsResponse(total, currentGenerationCount, waiting, reviewing, passed, failed);
    }

    public ApplicantListResponse getApplicants(ApplicationStatus status, Pageable pageable) {
        Page<Application> page = applicationRepository.search(status, pageable);
        var applicants = page.getContent().stream().map(ApplicantSummaryResponse::from).toList();
        return new ApplicantListResponse(applicants, page.getTotalElements(), page.hasNext());
    }

    public ApplicantDetailResponse getApplicantDetail(Long applicantId) {
        Application application = getApplicationOrThrow(applicantId);
        return ApplicantDetailResponse.from(application);
    }

    @Transactional
    public void updateStatus(Long applicantId, ApplicantStatusUpdateRequest request) {
        Application application = getApplicationOrThrow(applicantId);
        application.updateStatus(request.status());
    }

    private Application getApplicationOrThrow(Long applicantId) {
        return applicationRepository.findById(applicantId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
    }
}
