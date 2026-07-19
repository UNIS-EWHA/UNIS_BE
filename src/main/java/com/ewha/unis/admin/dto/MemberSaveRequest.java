package com.ewha.unis.admin.dto;

import com.ewha.unis.member.domain.MemberPart;
import com.ewha.unis.member.domain.MemberRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberSaveRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,
        @NotBlank(message = "학과는 필수입니다.")
        String department,
        @NotNull(message = "파트는 필수입니다.")
        MemberPart part,
        @NotNull(message = "역할은 필수입니다.")
        MemberRole role,
        @NotNull(message = "기수는 필수입니다.")
        Integer generation
) {
}
