package com.ewha.unis.community.dto;

import com.ewha.unis.community.entity.Recruitment;
import com.ewha.unis.community.entity.RecruitmentPart;
import com.ewha.unis.member.domain.MemberPart;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public record RecruitmentSummaryResponse(
        Long recruitmentId,
        List<MemberPart> parts,
        String title,
        String content,
        int viewCount,
        LocalDateTime createdAt,
        LocalDate deadline,
        Long dDay
) {
    public static RecruitmentSummaryResponse from(Recruitment recruitment) {
        LocalDate deadline = recruitment.getDeadline();
        Long dDay = deadline == null ? null : ChronoUnit.DAYS.between(LocalDate.now(), deadline);
        return new RecruitmentSummaryResponse(
                recruitment.getId(),
                recruitment.getParts().stream().map(RecruitmentPart::getPart).toList(),
                recruitment.getTitle(),
                recruitment.getContent(),
                recruitment.getViewCount(),
                recruitment.getCreatedAt(),
                deadline,
                dDay
        );
    }
}
