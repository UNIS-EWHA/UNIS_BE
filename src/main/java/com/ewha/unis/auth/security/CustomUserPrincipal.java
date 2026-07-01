package com.ewha.unis.auth.security;

import com.ewha.unis.member.domain.Role;

public record CustomUserPrincipal(
        Long memberId,
        Role role
) {
}
