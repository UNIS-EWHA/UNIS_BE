package com.ewha.unis.admin.dto;

import com.ewha.unis.member.domain.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminAccountGrantRequest(
        @NotBlank(message = "아이디는 필수입니다.")
        String loginId,
        @NotNull(message = "권한은 필수입니다.")
        Role role
) {
}
