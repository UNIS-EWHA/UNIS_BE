package com.ewha.unis.auth.dto;

import com.ewha.unis.member.domain.MemberPart;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,
        @NotBlank(message = "이메일 확인은 필수입니다.")
        String emailVerificationToken,
        @NotBlank(message = "아이디는 필수입니다.")
        String loginId,
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,
        Integer generation,
        MemberPart memberPart,
        @AssertTrue
        Boolean termsAgreed,
        @AssertTrue
        Boolean privacyAgreed
) {
}
