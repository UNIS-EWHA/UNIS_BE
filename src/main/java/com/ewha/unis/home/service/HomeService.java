package com.ewha.unis.home.service;

import com.ewha.unis.home.dto.ArchiveResponse;
import com.ewha.unis.home.dto.HomeStatsResponse;
import com.ewha.unis.home.dto.TestimonialResponse;
import com.ewha.unis.home.repository.ArchiveRepository;
import com.ewha.unis.home.repository.TestimonialRepository;
import com.ewha.unis.member.repository.MemberRepository;
import com.ewha.unis.project.repository.ProjectRepository;
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

    public HomeStatsResponse getStats() {
        Integer generation = projectRepository.findMaxGeneration();
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
}
