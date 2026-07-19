package com.ewha.unis.admin.dto;

import com.ewha.unis.application.entity.Application;
import com.ewha.unis.application.entity.ApplicationStatus;
import com.ewha.unis.member.domain.MemberPart;

import java.time.LocalDateTime;

public record ApplicantDetailResponse(
        Long applicantId,
        String name,
        String department,
        MemberPart part,
        String studentId,
        ApplicationStatus status,
        LocalDateTime appliedAt,
        String phone,
        String selfIntroduction,
        String motivation,
        String projectExperience,
        String conflictExperience,
        String portfolioUrl
) {
    public static ApplicantDetailResponse from(Application application) {
        return new ApplicantDetailResponse(
                application.getId(),
                application.getName(),
                application.getDepartment(),
                application.getPart(),
                application.getStudentId(),
                application.getStatus(),
                application.getCreatedAt(),
                application.getPhone(),
                application.getSelfIntroduction(),
                application.getMotivation(),
                application.getProjectExperience(),
                application.getConflictExperience(),
                application.getPortfolioUrl()
        );
    }
}
