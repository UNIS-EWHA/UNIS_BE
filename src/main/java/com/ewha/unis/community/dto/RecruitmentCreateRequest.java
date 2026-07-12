package com.ewha.unis.community.dto;

import com.ewha.unis.community.entity.RecruitmentType;
import com.ewha.unis.member.domain.MemberPart;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record RecruitmentCreateRequest(
        @NotNull(message = "모집 유형은 필수입니다.")
        RecruitmentType type,
        @NotBlank(message = "제목은 필수입니다.")
        String title,
        @NotBlank(message = "상세 내용은 필수입니다.")
        String content,
        @NotEmpty(message = "구하는 파트는 최소 1개 이상이어야 합니다.")
        List<MemberPart> parts,
        LocalDate deadline
) {
}
