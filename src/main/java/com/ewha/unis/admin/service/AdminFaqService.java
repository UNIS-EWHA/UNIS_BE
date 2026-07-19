package com.ewha.unis.admin.service;

import com.ewha.unis.about.dto.FaqResponse;
import com.ewha.unis.about.entity.Faq;
import com.ewha.unis.about.repository.FaqRepository;
import com.ewha.unis.admin.dto.FaqCreateResponse;
import com.ewha.unis.admin.dto.FaqRequest;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminFaqService {
    private final FaqRepository faqRepository;

    public List<FaqResponse> getFaqs() {
        return faqRepository.findAllByOrderBySortOrderAsc().stream()
                .map(FaqResponse::from)
                .toList();
    }

    @Transactional
    public FaqCreateResponse createFaq(FaqRequest request) {
        int nextOrder = faqRepository.findMaxSortOrder() + 1;
        Faq faq = Faq.builder()
                .question(request.question())
                .answer(request.answer())
                .sortOrder(nextOrder)
                .build();
        faqRepository.save(faq);
        return new FaqCreateResponse(faq.getId());
    }

    @Transactional
    public void updateFaq(Long faqId, FaqRequest request) {
        Faq faq = getFaqOrThrow(faqId);
        faq.update(request.question(), request.answer());
    }

    @Transactional
    public void deleteFaq(Long faqId) {
        Faq faq = getFaqOrThrow(faqId);
        faqRepository.delete(faq);
    }

    private Faq getFaqOrThrow(Long faqId) {
        return faqRepository.findById(faqId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
    }
}
