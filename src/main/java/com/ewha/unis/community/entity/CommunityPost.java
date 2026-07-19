package com.ewha.unis.community.entity;

import com.ewha.unis.global.entity.BaseTimeEntity;
import com.ewha.unis.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "community_posts")
public class CommunityPost extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostCategory category;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(length = 100)
    private String organizer;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "external_url", length = 500)
    private String externalUrl;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    private LocalDate deadline;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> tags = new ArrayList<>();

    @Builder
    private CommunityPost(Member author, PostCategory category, String title, String content,
                           String organizer, LocalDate startDate, LocalDate endDate,
                           String externalUrl, String imageUrl, LocalDate deadline) {
        this.author = author;
        this.category = category;
        this.title = title;
        this.content = content;
        this.organizer = organizer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.externalUrl = externalUrl;
        this.imageUrl = imageUrl;
        this.deadline = deadline;
        this.viewCount = 0;
    }

    public void addTag(PostTag tag) {
        tags.add(tag);
        tag.assignPost(this);
    }

    public void clearTags() {
        tags.clear();
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public boolean isOwnedBy(Long memberId) {
        return author.getId().equals(memberId);
    }

    public void update(PostCategory category, String title, String content, String organizer,
                        LocalDate startDate, LocalDate endDate, String externalUrl,
                        String imageUrl, LocalDate deadline) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.organizer = organizer;
        this.startDate = startDate;
        this.endDate = endDate;
        this.externalUrl = externalUrl;
        this.imageUrl = imageUrl;
        this.deadline = deadline;
    }
}
