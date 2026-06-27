package com.ewha.unis.auth.dto;

public record EmailVerifyResponse(
        String emailVerificationToken
) {
    public static EmailVerifyResponse of(String token) {
        return new EmailVerifyResponse(token);
    }
}
