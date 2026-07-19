package com.ewha.unis.application.dto;

import com.ewha.unis.member.domain.MemberPart;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ApplicationCreateRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,
        @NotBlank(message = "전화번호는 필수입니다.")
        String phone,
        @NotBlank(message = "학번은 필수입니다.")
        String studentId,
        @NotBlank(message = "학과는 필수입니다.")
        String department,
        @NotNull(message = "지원 파트는 필수입니다.")
        MemberPart part,
        @NotBlank(message = "자기소개는 필수입니다.")
        @Size(max = 300, message = "자기소개는 300자 이내로 작성해주세요.")
        String selfIntroduction,
        @NotBlank(message = "지원 동기는 필수입니다.")
        @Size(max = 500, message = "지원 동기는 500자 이내로 작성해주세요.")
        String motivation,
        @NotBlank(message = "프로젝트 경험은 필수입니다.")
        @Size(max = 500, message = "프로젝트 경험은 500자 이내로 작성해주세요.")
        String projectExperience,
        @NotBlank(message = "갈등 해결 경험은 필수입니다.")
        @Size(max = 500, message = "갈등 해결 경험은 500자 이내로 작성해주세요.")
        String conflictExperience,
        String portfolioUrl
) {
}
