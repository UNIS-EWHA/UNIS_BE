package com.ewha.unis.auth.dto;

import com.ewha.unis.member.domain.Role;

public record LoginResult(
        String accessToken,
        String refreshToken,
        long refreshTokenValidity,
        Role role
) {
}
