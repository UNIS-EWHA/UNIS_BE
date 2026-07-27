package com.ewha.unis.about.service;

import com.ewha.unis.about.dto.AboutExperienceResponse;
import com.ewha.unis.about.dto.AboutIntroResponse;
import com.ewha.unis.about.dto.AboutResponse;
import com.ewha.unis.about.dto.CoreValueResponse;
import com.ewha.unis.about.dto.FaqResponse;
import com.ewha.unis.about.dto.PartResponse;
import com.ewha.unis.about.dto.PhotoResponse;
import com.ewha.unis.about.repository.CoreValueRepository;
import com.ewha.unis.about.repository.FaqRepository;
import com.ewha.unis.about.repository.PartInfoRepository;
import com.ewha.unis.about.repository.PhotoRepository;
import com.ewha.unis.admin.entity.PageContent;
import com.ewha.unis.admin.entity.PageSection;
import com.ewha.unis.admin.repository.PageContentRepository;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AboutService {
    private final CoreValueRepository coreValueRepository;
    private final PartInfoRepository partInfoRepository;
    private final PhotoRepository photoRepository;
    private final FaqRepository faqRepository;
    private final PageContentRepository pageContentRepository;
    private final ObjectMapper objectMapper;

    public AboutResponse getAbout() {
        return new AboutResponse(
                coreValueRepository.findAllByOrderBySortOrderAsc().stream()
                        .map(CoreValueResponse::from).toList(),
                partInfoRepository.findAllByOrderBySortOrderAsc().stream()
                        .map(PartResponse::from).toList(),
                photoRepository.findAllByOrderBySortOrderAsc().stream()
                        .map(PhotoResponse::from).toList(),
                faqRepository.findAllByOrderBySortOrderAsc().stream()
                        .map(FaqResponse::from).toList()
        );
    }

    public AboutIntroResponse getIntro() {
        PageContent pageContent = pageContentRepository.findBySection(PageSection.ABOUT_INTRO)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return readContent(pageContent, AboutIntroResponse.class);
    }

    public AboutExperienceResponse getExperience() {
        PageContent pageContent = pageContentRepository.findBySection(PageSection.ABOUT_EXPERIENCE)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return readContent(pageContent, AboutExperienceResponse.class);
    }

    private <T> T readContent(PageContent pageContent, Class<T> type) {
        try {
            return objectMapper.readValue(pageContent.getContentJson(), type);
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
