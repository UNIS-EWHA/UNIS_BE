package com.ewha.unis.admin.service;

import com.ewha.unis.admin.dto.AdminPostListResponse;
import com.ewha.unis.admin.dto.AdminPostSummaryResponse;
import com.ewha.unis.admin.repository.AdminCommunityQueryRepository;
import com.ewha.unis.community.entity.CommunityPost;
import com.ewha.unis.community.entity.Recruitment;
import com.ewha.unis.community.entity.SavedTargetType;
import com.ewha.unis.community.repository.CommentRepository;
import com.ewha.unis.community.repository.CommunityPostRepository;
import com.ewha.unis.community.repository.RecruitmentRepository;
import com.ewha.unis.community.repository.SavedItemRepository;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCommunityService {
    private final CommunityPostRepository communityPostRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final SavedItemRepository savedItemRepository;
    private final CommentRepository commentRepository;
    private final AdminCommunityQueryRepository adminCommunityQueryRepository;

    public AdminPostListResponse getPosts(SavedTargetType type, Pageable pageable) {
        if (type == SavedTargetType.POST) {
            Page<CommunityPost> page = communityPostRepository.search(null, null, pageable);
            return new AdminPostListResponse(
                    page.getContent().stream().map(AdminPostSummaryResponse::fromPost).toList(),
                    page.getTotalElements(), page.hasNext());
        }
        if (type == SavedTargetType.RECRUITMENT) {
            Page<Recruitment> page = recruitmentRepository.search(null, null, pageable);
            return new AdminPostListResponse(
                    page.getContent().stream().map(AdminPostSummaryResponse::fromRecruitment).toList(),
                    page.getTotalElements(), page.hasNext());
        }

        long offset = (long) pageable.getPageNumber() * pageable.getPageSize();
        List<AdminPostSummaryResponse> posts = adminCommunityQueryRepository
                .findAllCombined(pageable.getPageSize(), offset).stream()
                .map(AdminPostSummaryResponse::from)
                .toList();
        long totalCount = adminCommunityQueryRepository.countCombined();
        boolean hasNext = offset + posts.size() < totalCount;
        return new AdminPostListResponse(posts, totalCount, hasNext);
    }

    @Transactional
    public void deletePost(Long postId) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        commentRepository.deleteAllByPost_Id(postId);
        communityPostRepository.delete(post);
        savedItemRepository.deleteAllByTargetTypeAndTargetId(SavedTargetType.POST, postId);
    }

    @Transactional
    public void deleteRecruitment(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        recruitmentRepository.delete(recruitment);
        savedItemRepository.deleteAllByTargetTypeAndTargetId(SavedTargetType.RECRUITMENT, recruitmentId);
    }
}
