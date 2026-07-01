package com.ewha.unis.auth.dto;

import com.ewha.unis.member.domain.Role;

public record LoginResponse(
        String accessToken,
        Role role
) {
}
