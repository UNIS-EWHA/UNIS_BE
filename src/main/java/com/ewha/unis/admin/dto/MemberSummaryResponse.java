package com.ewha.unis.admin.dto;

import com.ewha.unis.member.domain.Member;
import com.ewha.unis.member.domain.MemberPart;
import com.ewha.unis.member.domain.MemberRole;

public record MemberSummaryResponse(
        Long memberId,
        String name,
        String department,
        MemberPart part,
        MemberRole role,
        Integer generation
) {
    public static MemberSummaryResponse from(Member member) {
        return new MemberSummaryResponse(
                member.getId(),
                member.getName(),
                member.getDepartment(),
                member.getPart(),
                member.getMemberRole(),
                member.getGeneration()
        );
    }
}
