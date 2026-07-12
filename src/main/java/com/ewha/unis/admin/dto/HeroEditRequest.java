package com.ewha.unis.admin.dto;

import jakarta.validation.constraints.NotBlank;

public record HeroEditRequest(
        @NotBlank(message = "메인 제목은 필수입니다.")
        String mainTitle,
        @NotBlank(message = "서브 문구는 필수입니다.")
        String subTitle,
        @NotBlank(message = "CTA 버튼 텍스트는 필수입니다.")
        String ctaText
) {
}
