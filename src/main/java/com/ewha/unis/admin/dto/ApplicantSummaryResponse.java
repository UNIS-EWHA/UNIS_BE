package com.ewha.unis.admin.dto;

import com.ewha.unis.application.entity.Application;
import com.ewha.unis.application.entity.ApplicationStatus;
import com.ewha.unis.member.domain.MemberPart;

import java.time.LocalDateTime;

public record ApplicantSummaryResponse(
        Long applicantId,
        String name,
        String department,
        MemberPart part,
        String studentId,
        ApplicationStatus status,
        LocalDateTime appliedAt
) {
    public static ApplicantSummaryResponse from(Application application) {
        return new ApplicantSummaryResponse(
                application.getId(),
                application.getName(),
                application.getDepartment(),
                application.getPart(),
                application.getStudentId(),
                application.getStatus(),
                application.getCreatedAt()
        );
    }
}
