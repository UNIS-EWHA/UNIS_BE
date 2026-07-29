package com.ewha.unis.application.entity;

import com.ewha.unis.global.entity.BaseTimeEntity;
import com.ewha.unis.member.domain.Member;
import com.ewha.unis.member.domain.MemberPart;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "applications")
public class Application extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(name = "student_id", nullable = false, length = 20)
    private String studentId;

    @Column(nullable = false, length = 150)
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberPart part;

    @Column(nullable = false)
    private Integer generation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(name = "self_introduction", nullable = false)
    private String selfIntroduction;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(nullable = false)
    private String motivation;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(name = "project_experience", nullable = false)
    private String projectExperience;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(name = "conflict_experience", nullable = false)
    private String conflictExperience;

    @Column(name = "portfolio_url", length = 500)
    private String portfolioUrl;

    @Builder
    private Application(Member member, String name, String phone, String studentId, String department,
                         MemberPart part, Integer generation, String selfIntroduction, String motivation,
                         String projectExperience, String conflictExperience, String portfolioUrl) {
        this.member = member;
        this.name = name;
        this.phone = phone;
        this.studentId = studentId;
        this.department = department;
        this.part = part;
        this.generation = generation;
        this.status = ApplicationStatus.WAITING;
        this.selfIntroduction = selfIntroduction;
        this.motivation = motivation;
        this.projectExperience = projectExperience;
        this.conflictExperience = conflictExperience;
        this.portfolioUrl = portfolioUrl;
    }

    public void updateStatus(ApplicationStatus status) {
        this.status = status;
    }
}
