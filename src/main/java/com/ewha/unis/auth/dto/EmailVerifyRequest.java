package com.ewha.unis.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailVerifyRequest(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String code
) { }
