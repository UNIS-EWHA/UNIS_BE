package com.ewha.unis.application.service;

import com.ewha.unis.admin.entity.RecruitSettings;
import com.ewha.unis.admin.entity.RecruitStatus;
import com.ewha.unis.admin.repository.RecruitSettingsRepository;
import com.ewha.unis.application.dto.ApplicationCreateRequest;
import com.ewha.unis.application.dto.ApplicationCreateResponse;
import com.ewha.unis.application.dto.RecruitInfoResponse;
import com.ewha.unis.application.dto.StudentIdCheckResponse;
import com.ewha.unis.application.entity.Application;
import com.ewha.unis.application.repository.ApplicationRepository;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import com.ewha.unis.member.domain.Member;
import com.ewha.unis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final RecruitSettingsRepository recruitSettingsRepository;
    private final MemberRepository memberRepository;

    public RecruitInfoResponse getRecruitInfo() {
        RecruitSettings settings = getCurrentRecruitSettings();
        return RecruitInfoResponse.from(settings);
    }

    public StudentIdCheckResponse checkStudentId(String studentId) {
        RecruitSettings settings = getCurrentRecruitSettings();
        boolean exists = applicationRepository.existsByStudentIdAndGeneration(studentId, settings.getGeneration());
        return StudentIdCheckResponse.of(!exists);
    }

    @Transactional
    public ApplicationCreateResponse submit(ApplicationCreateRequest request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        RecruitSettings settings = getCurrentRecruitSettings();

        if (settings.getStatus() != RecruitStatus.OPEN) {
            throw new CustomException(ErrorCode.RECRUITMENT_NOT_OPEN);
        }

        if (applicationRepository.existsByStudentIdAndGeneration(request.studentId(), settings.getGeneration())) {
            throw new CustomException(ErrorCode.DUPLICATE_APPLICATION);
        }

        Application application = Application.builder()
                .member(member)
                .name(request.name())
                .phone(request.phone())
                .studentId(request.studentId())
                .department(request.department())
                .part(request.part())
                .generation(settings.getGeneration())
                .selfIntroduction(request.selfIntroduction())
                .motivation(request.motivation())
                .projectExperience(request.projectExperience())
                .conflictExperience(request.conflictExperience())
                .portfolioUrl(request.portfolioUrl())
                .build();
        applicationRepository.save(application);

        return new ApplicationCreateResponse(application.getId(), settings.getResultAnnounceAt());
    }

    private RecruitSettings getCurrentRecruitSettings() {
        return recruitSettingsRepository.findTopByOrderByIdDesc()
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
    }
}
