package com.ewha.unis.admin.service;

import com.ewha.unis.admin.dto.RecruitSettingsRequest;
import com.ewha.unis.admin.dto.RecruitSettingsResponse;
import com.ewha.unis.admin.entity.RecruitSettings;
import com.ewha.unis.admin.repository.RecruitSettingsRepository;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitSettingsService {
    private final RecruitSettingsRepository recruitSettingsRepository;

    public RecruitSettingsResponse getSettings() {
        RecruitSettings settings = recruitSettingsRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return RecruitSettingsResponse.from(settings);
    }

    @Transactional
    public void saveSettings(RecruitSettingsRequest request) {
        recruitSettingsRepository.findTopByOrderByIdDesc()
                .ifPresentOrElse(
                        existing -> existing.update(request.generation(), request.status(),
                                request.startDate(), request.endDate(), request.resultAnnounceAt(),
                                request.capacity(), request.schedule()),
                        () -> recruitSettingsRepository.save(RecruitSettings.builder()
                                .generation(request.generation())
                                .status(request.status())
                                .startDate(request.startDate())
                                .endDate(request.endDate())
                                .resultAnnounceAt(request.resultAnnounceAt())
                                .capacity(request.capacity())
                                .schedule(request.schedule())
                                .build())
                );
    }
}
