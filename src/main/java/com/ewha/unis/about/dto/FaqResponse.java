package com.ewha.unis.about.dto;

import com.ewha.unis.about.entity.Faq;

public record FaqResponse(
        Long faqId,
        String question,
        String answer
) {
    public static FaqResponse from(Faq faq) {
        return new FaqResponse(faq.getId(), faq.getQuestion(), faq.getAnswer());
    }
}
