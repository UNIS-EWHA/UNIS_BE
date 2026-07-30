package com.ewha.unis.admin.service;

import com.ewha.unis.about.entity.CoreValue;
import com.ewha.unis.about.entity.PartInfo;
import com.ewha.unis.about.entity.PartTag;
import com.ewha.unis.about.entity.Photo;
import com.ewha.unis.about.repository.CoreValueRepository;
import com.ewha.unis.about.repository.PartInfoRepository;
import com.ewha.unis.about.repository.PhotoRepository;
import com.ewha.unis.admin.dto.AboutIntroEditRequest;
import com.ewha.unis.admin.dto.CoreValueItemDto;
import com.ewha.unis.admin.dto.CoreValuesEditRequest;
import com.ewha.unis.admin.dto.ExperienceEditRequest;
import com.ewha.unis.admin.dto.HeroEditRequest;
import com.ewha.unis.admin.dto.PartItemDto;
import com.ewha.unis.admin.dto.PartsEditRequest;
import com.ewha.unis.admin.dto.PhotoItemDto;
import com.ewha.unis.admin.dto.PhotosEditRequest;
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
    private final PartInfoRepository partInfoRepository;
    private final PhotoRepository photoRepository;
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

    @Transactional
    public void updateParts(PartsEditRequest request) {
        partInfoRepository.deleteAll();

        List<PartInfo> partInfos = new ArrayList<>();
        int order = 0;
        for (PartItemDto item : request.parts()) {
            PartInfo partInfo = PartInfo.builder()
                    .name(item.name())
                    .description(item.description())
                    .sortOrder(order++)
                    .build();
            for (String tag : item.tags()) {
                partInfo.addTag(PartTag.builder().tagName(tag).build());
            }
            partInfos.add(partInfo);
        }
        partInfoRepository.saveAll(partInfos);
    }

    @Transactional
    public void updatePhotos(PhotosEditRequest request) {
        photoRepository.deleteAll();

        List<Photo> photos = new ArrayList<>();
        int order = 0;
        for (PhotoItemDto item : request.photos()) {
            photos.add(Photo.builder()
                    .label(item.label())
                    .imageUrl(item.imageUrl())
                    .sortOrder(order++)
                    .build());
        }
        photoRepository.saveAll(photos);
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
