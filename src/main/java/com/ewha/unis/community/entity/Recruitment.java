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
@Table(name = "recruitments")
public class Recruitment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member author;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentType type;

    @Column(nullable = false, length = 200)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    private LocalDate deadline;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @OneToMany(mappedBy = "recruitment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitmentPart> parts = new ArrayList<>();

    @Builder
    private Recruitment(Member author, RecruitmentType type, String title, String content, LocalDate deadline) {
        this.author = author;
        this.type = type;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
        this.viewCount = 0;
    }

    public void addPart(RecruitmentPart part) {
        parts.add(part);
        part.assignRecruitment(this);
    }

    public void clearParts() {
        parts.clear();
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public boolean isOwnedBy(Long memberId) {
        return author.getId().equals(memberId);
    }

    public void update(RecruitmentType type, String title, String content, LocalDate deadline) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.deadline = deadline;
    }
}
