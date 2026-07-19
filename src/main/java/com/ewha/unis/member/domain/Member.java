package com.ewha.unis.member.domain;

import com.ewha.unis.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String loginId;

    @Column(length = 255)
    private String password;

    @Column(unique = true)
    private String email;

    private Integer generation;

    @Enumerated(EnumType.STRING)
    @Column(name = "part")
    private MemberPart part;

    @Column(length = 150)
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false)
    private MemberRole memberRole;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    private LocalDateTime termsAgreedAt;

    private LocalDateTime privacyAgreedAt;

    @Builder
    private Member(String name, String email, String loginId, String encodedPassword,
                   Integer generation, MemberPart part, String department, MemberRole memberRole) {
        this.name = name;
        this.email = email;
        this.loginId = loginId;
        this.password = encodedPassword;
        this.generation = generation;
        this.part = part;
        this.department = department;
        this.role = Role.USER;
        this.memberRole = memberRole != null ? memberRole : MemberRole.GENERAL;
        if (loginId != null) {
            this.termsAgreedAt = LocalDateTime.now();
            this.privacyAgreedAt = LocalDateTime.now();
        }
    }

    public void updateLastLogin() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public void updateRosterInfo(String name, String department, MemberPart part,
                                  MemberRole memberRole, Integer generation) {
        this.name = name;
        this.department = department;
        this.part = part;
        this.memberRole = memberRole;
        this.generation = generation;
    }

    public void promoteTo(Role role) {
        this.role = role;
    }
}
