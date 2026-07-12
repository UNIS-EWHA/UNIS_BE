package com.ewha.unis.community.entity;

import com.ewha.unis.global.entity.BaseTimeEntity;
import com.ewha.unis.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comments")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityPost post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member author;

    @Lob
    @Column(nullable = false)
    private String content;

    @Builder
    private Comment(CommunityPost post, Member author, String content) {
        this.post = post;
        this.author = author;
        this.content = content;
    }

    public boolean isOwnedBy(Long memberId) {
        return author.getId().equals(memberId);
    }

    public boolean belongsToPost(Long postId) {
        return post.getId().equals(postId);
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
