package com.ewha.unis.admin.dto;

import com.ewha.unis.member.domain.Member;
import com.ewha.unis.member.domain.Role;

import java.time.LocalDateTime;

public record AdminAccountResponse(
        Long accountId,
        String name,
        String loginId,
        Role role,
        LocalDateTime lastLoginAt
) {
    public static AdminAccountResponse from(Member member) {
        return new AdminAccountResponse(
                member.getId(),
                member.getName(),
                member.getLoginId(),
                member.getRole(),
                member.getLastLoginAt()
        );
    }
}
