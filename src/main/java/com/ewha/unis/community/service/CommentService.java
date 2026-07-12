package com.ewha.unis.community.service;

import com.ewha.unis.community.dto.CommentCreateResponse;
import com.ewha.unis.community.dto.CommentRequest;
import com.ewha.unis.community.dto.CommentResponse;
import com.ewha.unis.community.entity.Comment;
import com.ewha.unis.community.entity.CommunityPost;
import com.ewha.unis.community.repository.CommentRepository;
import com.ewha.unis.community.repository.CommunityPostRepository;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import com.ewha.unis.member.domain.Member;
import com.ewha.unis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommunityPostRepository communityPostRepository;
    private final MemberRepository memberRepository;

    public List<CommentResponse> getComments(Long postId, Long currentUserId) {
        if (!communityPostRepository.existsById(postId)) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return commentRepository.findAllByPost_IdOrderByCreatedAtAsc(postId).stream()
                .map(comment -> CommentResponse.of(comment, comment.isOwnedBy(currentUserId)))
                .toList();
    }

    @Transactional
    public CommentCreateResponse createComment(Long postId, CommentRequest request, Long authorId) {
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        Member author = memberRepository.findById(authorId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));

        Comment comment = Comment.builder()
                .post(post)
                .author(author)
                .content(request.content())
                .build();
        commentRepository.save(comment);
        return new CommentCreateResponse(comment.getId());
    }

    @Transactional
    public void updateComment(Long postId, Long commentId, CommentRequest request, Long currentUserId) {
        Comment comment = getCommentInPostOrThrow(postId, commentId);
        if (!comment.isOwnedBy(currentUserId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        comment.updateContent(request.content());
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, Long currentUserId) {
        Comment comment = getCommentInPostOrThrow(postId, commentId);
        if (!comment.isOwnedBy(currentUserId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        commentRepository.delete(comment);
    }

    private Comment getCommentInPostOrThrow(Long postId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        if (!comment.belongsToPost(postId)) {
            throw new CustomException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        return comment;
    }
}
