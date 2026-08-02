package com.ewha.unis.home.service;

import com.ewha.unis.admin.entity.PageContent;
import com.ewha.unis.admin.entity.PageSection;
import com.ewha.unis.admin.repository.PageContentRepository;
import com.ewha.unis.admin.repository.RecruitSettingsRepository;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import com.ewha.unis.home.dto.ArchiveResponse;
import com.ewha.unis.home.dto.HeroResponse;
import com.ewha.unis.home.dto.HomeStatsResponse;
import com.ewha.unis.home.dto.TestimonialResponse;
import com.ewha.unis.home.repository.ArchiveRepository;
import com.ewha.unis.home.repository.TestimonialRepository;
import com.ewha.unis.member.repository.MemberRepository;
import com.ewha.unis.project.repository.ProjectRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {
    // TODO: 수상 경력을 관리하는 기능이 정해지기 전까지의 임시 고정값
    private static final int PLACEHOLDER_AWARD_COUNT = 0;

    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final ArchiveRepository archiveRepository;
    private final TestimonialRepository testimonialRepository;
    private final RecruitSettingsRepository recruitSettingsRepository;
    private final PageContentRepository pageContentRepository;
    private final ObjectMapper objectMapper;

    public HomeStatsResponse getStats() {
        Integer generation = recruitSettingsRepository.findTopByOrderByIdDesc()
                .map(s -> s.getGeneration() - 1)
                .orElseGet(projectRepository::findMaxGeneration);
        long projectCount = projectRepository.count();
        long memberCount = memberRepository.count();
        return new HomeStatsResponse(generation, projectCount, memberCount, PLACEHOLDER_AWARD_COUNT);
    }

    public List<ArchiveResponse> getArchives() {
        return archiveRepository.findAllByOrderBySortOrderAsc().stream()
                .map(ArchiveResponse::from)
                .toList();
    }

    public List<TestimonialResponse> getTestimonials() {
        return testimonialRepository.findAllByOrderBySortOrderAsc().stream()
                .map(TestimonialResponse::from)
                .toList();
    }

    public HeroResponse getHero() {
        PageContent pageContent = pageContentRepository.findBySection(PageSection.HERO)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        return readContent(pageContent, HeroResponse.class);
    }

    private <T> T readContent(PageContent pageContent, Class<T> type) {
        try {
            return objectMapper.readValue(pageContent.getContentJson(), type);
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
