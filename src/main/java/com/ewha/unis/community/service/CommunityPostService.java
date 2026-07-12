package com.ewha.unis.community.service;

import com.ewha.unis.community.dto.CommunityPostCreateRequest;
import com.ewha.unis.community.dto.CommunityPostCreateResponse;
import com.ewha.unis.community.dto.CommunityPostDetailResponse;
import com.ewha.unis.community.dto.CommunityPostListResponse;
import com.ewha.unis.community.dto.CommunityPostSummaryResponse;
import com.ewha.unis.community.dto.PostTagDto;
import com.ewha.unis.community.dto.SaveToggleResponse;
import com.ewha.unis.community.entity.CommunityPost;
import com.ewha.unis.community.entity.PostCategory;
import com.ewha.unis.community.entity.PostTag;
import com.ewha.unis.community.entity.SavedItem;
import com.ewha.unis.community.entity.SavedTargetType;
import com.ewha.unis.community.repository.CommentRepository;
import com.ewha.unis.community.repository.CommunityPostRepository;
import com.ewha.unis.community.repository.SavedItemRepository;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import com.ewha.unis.member.domain.Member;
import com.ewha.unis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityPostService {
    private final CommunityPostRepository communityPostRepository;
    private final MemberRepository memberRepository;
    private final SavedItemRepository savedItemRepository;
    private final CommentRepository commentRepository;

    public CommunityPostListResponse getPosts(PostCategory category, String keyword,
                                               Pageable pageable, Long currentUserId) {
        Page<CommunityPost> page = communityPostRepository.search(category, keyword, pageable);
        Set<Long> savedPostIds = savedItemRepository.findAllByUser_IdAndTargetType(currentUserId, SavedTargetType.POST)
                .stream().map(SavedItem::getTargetId).collect(Collectors.toSet());

        List<CommunityPostSummaryResponse> posts = page.getContent().stream()
                .map(post -> CommunityPostSummaryResponse.of(post, savedPostIds.contains(post.getId())))
                .toList();
        return new CommunityPostListResponse(posts, page.getTotalElements(), page.hasNext());
    }

    @Transactional
    public CommunityPostDetailResponse getPostDetail(Long postId, Long currentUserId) {
        CommunityPost post = getPostOrThrow(postId);
        post.increaseViewCount();

        boolean isSaved = savedItemRepository
                .findByUser_IdAndTargetTypeAndTargetId(currentUserId, SavedTargetType.POST, postId)
                .isPresent();
        return CommunityPostDetailResponse.of(post, isSaved, post.isOwnedBy(currentUserId));
    }

    @Transactional
    public CommunityPostCreateResponse createPost(CommunityPostCreateRequest request, Long authorId) {
        Member author = memberRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        CommunityPost post = CommunityPost.builder()
                .author(author)
                .category(request.category())
                .title(request.title())
                .content(request.content())
                .organizer(request.organizer())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .externalUrl(request.externalUrl())
                .imageUrl(request.imageUrl())
                .deadline(request.deadline())
                .build();
        addTags(post, request.tags());

        communityPostRepository.save(post);
        return new CommunityPostCreateResponse(post.getId());
    }

    @Transactional
    public void updatePost(Long postId, CommunityPostCreateRequest request, Long currentUserId) {
        CommunityPost post = getPostOrThrow(postId);
        if (!post.isOwnedBy(currentUserId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        post.update(request.category(), request.title(), request.content(), request.organizer(),
                request.startDate(), request.endDate(), request.externalUrl(),
                request.imageUrl(), request.deadline());
        post.clearTags();
        addTags(post, request.tags());
    }

    @Transactional
    public void deletePost(Long postId, Long currentUserId) {
        CommunityPost post = getPostOrThrow(postId);
        if (!post.isOwnedBy(currentUserId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        commentRepository.deleteAllByPost_Id(postId);
        communityPostRepository.delete(post);
        savedItemRepository.deleteAllByTargetTypeAndTargetId(SavedTargetType.POST, postId);
    }

    @Transactional
    public SaveToggleResponse toggleSave(Long postId, Long currentUserId) {
        getPostOrThrow(postId);
        Member user = memberRepository.findById(currentUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        return savedItemRepository.findByUser_IdAndTargetTypeAndTargetId(currentUserId, SavedTargetType.POST, postId)
                .map(saved -> {
                    savedItemRepository.delete(saved);
                    return SaveToggleResponse.of(false);
                })
                .orElseGet(() -> {
                    savedItemRepository.save(SavedItem.builder()
                            .user(user)
                            .targetType(SavedTargetType.POST)
                            .targetId(postId)
                            .build());
                    return SaveToggleResponse.of(true);
                });
    }

    private void addTags(CommunityPost post, List<PostTagDto> tags) {
        if (tags == null) {
            return;
        }
        tags.forEach(tag -> post.addTag(PostTag.builder()
                .label(tag.label())
                .content(tag.content())
                .build()));
    }

    private CommunityPost getPostOrThrow(Long postId) {
        return communityPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
    }
}
