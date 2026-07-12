package com.ewha.unis.project.dto;

import com.ewha.unis.member.domain.MemberPart;
import com.ewha.unis.project.entity.ProjectMember;

public record ProjectMemberResponse(
        MemberPart part,
        String name
) {
    public static ProjectMemberResponse from(ProjectMember member) {
        return new ProjectMemberResponse(member.getPart(), member.getMemberName());
    }
}
