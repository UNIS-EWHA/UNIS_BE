package com.ewha.unis.community.service;

import com.ewha.unis.community.dto.RecruitmentCreateRequest;
import com.ewha.unis.community.dto.RecruitmentCreateResponse;
import com.ewha.unis.community.dto.RecruitmentDetailResponse;
import com.ewha.unis.community.dto.RecruitmentListResponse;
import com.ewha.unis.community.dto.RecruitmentSummaryResponse;
import com.ewha.unis.community.dto.SaveToggleResponse;
import com.ewha.unis.community.entity.Recruitment;
import com.ewha.unis.community.entity.RecruitmentPart;
import com.ewha.unis.community.entity.SavedItem;
import com.ewha.unis.community.entity.SavedTargetType;
import com.ewha.unis.community.repository.RecruitmentRepository;
import com.ewha.unis.community.repository.SavedItemRepository;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import com.ewha.unis.member.domain.Member;
import com.ewha.unis.member.domain.MemberPart;
import com.ewha.unis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitmentService {
    private final RecruitmentRepository recruitmentRepository;
    private final MemberRepository memberRepository;
    private final SavedItemRepository savedItemRepository;

    public RecruitmentListResponse getRecruitments(MemberPart part, String keyword, Pageable pageable) {
        Page<Recruitment> page = recruitmentRepository.search(part, keyword, pageable);
        List<RecruitmentSummaryResponse> recruitments = page.getContent().stream()
                .map(RecruitmentSummaryResponse::from)
                .toList();
        return new RecruitmentListResponse(recruitments, page.getTotalElements(), page.hasNext());
    }

    @Transactional
    public RecruitmentDetailResponse getRecruitmentDetail(Long recruitmentId, Long currentUserId) {
        Recruitment recruitment = getRecruitmentOrThrow(recruitmentId);
        recruitment.increaseViewCount();
        return RecruitmentDetailResponse.of(recruitment, recruitment.isOwnedBy(currentUserId));
    }

    @Transactional
    public RecruitmentCreateResponse createRecruitment(RecruitmentCreateRequest request, Long authorId) {
        Member author = memberRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        Recruitment recruitment = Recruitment.builder()
                .author(author)
                .type(request.type())
                .title(request.title())
                .content(request.content())
                .deadline(request.deadline())
                .build();
        addParts(recruitment, request.parts());

        recruitmentRepository.save(recruitment);
        return new RecruitmentCreateResponse(recruitment.getId());
    }

    @Transactional
    public void updateRecruitment(Long recruitmentId, RecruitmentCreateRequest request, Long currentUserId) {
        Recruitment recruitment = getRecruitmentOrThrow(recruitmentId);
        if (!recruitment.isOwnedBy(currentUserId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        recruitment.update(request.type(), request.title(), request.content(), request.deadline());
        recruitment.clearParts();
        addParts(recruitment, request.parts());
    }

    @Transactional
    public void deleteRecruitment(Long recruitmentId, Long currentUserId) {
        Recruitment recruitment = getRecruitmentOrThrow(recruitmentId);
        if (!recruitment.isOwnedBy(currentUserId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        recruitmentRepository.delete(recruitment);
        savedItemRepository.deleteAllByTargetTypeAndTargetId(SavedTargetType.RECRUITMENT, recruitmentId);
    }

    @Transactional
    public SaveToggleResponse toggleSave(Long recruitmentId, Long currentUserId) {
        getRecruitmentOrThrow(recruitmentId);
        Member user = memberRepository.findById(currentUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        return savedItemRepository.findByUser_IdAndTargetTypeAndTargetId(
                        currentUserId, SavedTargetType.RECRUITMENT, recruitmentId)
                .map(saved -> {
                    savedItemRepository.delete(saved);
                    return SaveToggleResponse.of(false);
                })
                .orElseGet(() -> {
                    savedItemRepository.save(SavedItem.builder()
                            .user(user)
                            .targetType(SavedTargetType.RECRUITMENT)
                            .targetId(recruitmentId)
                            .build());
                    return SaveToggleResponse.of(true);
                });
    }

    private void addParts(Recruitment recruitment, List<MemberPart> parts) {
        parts.forEach(part -> recruitment.addPart(RecruitmentPart.builder().part(part).build()));
    }

    private Recruitment getRecruitmentOrThrow(Long recruitmentId) {
        return recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
    }
}
