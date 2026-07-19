package com.ewha.unis.admin.service;

import com.ewha.unis.about.entity.CoreValue;
import com.ewha.unis.about.repository.CoreValueRepository;
import com.ewha.unis.admin.dto.AboutIntroEditRequest;
import com.ewha.unis.admin.dto.CoreValueItemDto;
import com.ewha.unis.admin.dto.CoreValuesEditRequest;
import com.ewha.unis.admin.dto.ExperienceEditRequest;
import com.ewha.unis.admin.dto.HeroEditRequest;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PageContentService {
    private final PageContentRepository pageContentRepository;
    private final CoreValueRepository coreValueRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void updateHero(HeroEditRequest request) {
        saveSection(PageSection.HERO, request);
    }

    @Transactional
    public void updateAboutIntro(AboutIntroEditRequest request) {
        saveSection(PageSection.ABOUT_INTRO, request);
    }

    @Transactional
    public void updateExperience(ExperienceEditRequest request) {
        saveSection(PageSection.ABOUT_EXPERIENCE, request);
    }

    @Transactional
    public void updateCoreValues(CoreValuesEditRequest request) {
        coreValueRepository.deleteAll();

        List<CoreValue> coreValues = new ArrayList<>();
        int order = 0;
        for (CoreValueItemDto item : request.values()) {
            coreValues.add(CoreValue.builder()
                    .title(item.title())
                    .description(item.description())
                    .sortOrder(order++)
                    .build());
        }
        coreValueRepository.saveAll(coreValues);
    }

    private void saveSection(PageSection section, Object content) {
        String json;
        try {
            json = objectMapper.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        pageContentRepository.findBySection(section)
                .ifPresentOrElse(
                        existing -> existing.updateContent(json),
                        () -> pageContentRepository.save(PageContent.builder()
                                .section(section)
                                .contentJson(json)
                                .build())
                );
    }
}
