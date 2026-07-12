package com.ewha.unis.community.service;

import com.ewha.unis.community.dto.SavedItemListResponse;
import com.ewha.unis.community.dto.SavedItemResponse;
import com.ewha.unis.community.entity.CommunityPost;
import com.ewha.unis.community.entity.Recruitment;
import com.ewha.unis.community.entity.SavedItem;
import com.ewha.unis.community.entity.SavedTargetType;
import com.ewha.unis.community.repository.CommunityPostRepository;
import com.ewha.unis.community.repository.RecruitmentRepository;
import com.ewha.unis.community.repository.SavedItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SavedItemService {
    private final SavedItemRepository savedItemRepository;
    private final CommunityPostRepository communityPostRepository;
    private final RecruitmentRepository recruitmentRepository;

    public SavedItemListResponse getSavedItems(SavedTargetType type, Long userId, Pageable pageable) {
        Page<SavedItem> page = savedItemRepository.search(userId, type, pageable);

        List<Long> postIds = page.getContent().stream()
                .filter(item -> item.getTargetType() == SavedTargetType.POST)
                .map(SavedItem::getTargetId)
                .toList();
        List<Long> recruitmentIds = page.getContent().stream()
                .filter(item -> item.getTargetType() == SavedTargetType.RECRUITMENT)
                .map(SavedItem::getTargetId)
                .toList();

        Map<Long, CommunityPost> posts = communityPostRepository.findAllById(postIds).stream()
                .collect(Collectors.toMap(CommunityPost::getId, Function.identity()));
        Map<Long, Recruitment> recruitments = recruitmentRepository.findAllById(recruitmentIds).stream()
                .collect(Collectors.toMap(Recruitment::getId, Function.identity()));

        List<SavedItemResponse> saved = page.getContent().stream()
                .map(item -> toResponse(item, posts, recruitments))
                .filter(response -> response != null)
                .toList();

        return new SavedItemListResponse(saved, page.getTotalElements(), page.hasNext());
    }

    private SavedItemResponse toResponse(SavedItem item, Map<Long, CommunityPost> posts,
                                          Map<Long, Recruitment> recruitments) {
        if (item.getTargetType() == SavedTargetType.POST) {
            CommunityPost post = posts.get(item.getTargetId());
            return post == null ? null : SavedItemResponse.ofPost(item, post);
        }
        Recruitment recruitment = recruitments.get(item.getTargetId());
        return recruitment == null ? null : SavedItemResponse.ofRecruitment(item, recruitment);
    }
}
