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

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private Integer generation;

    @Enumerated(EnumType.STRING)
    @Column(name = "part")
    private MemberPart part;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private LocalDateTime termsAgreedAt;

    @Column(nullable = false)
    private LocalDateTime privacyAgreedAt;

    @Builder
    private Member(String name, String email, String loginId, String encodedPassword,
                   Integer generation, MemberPart part) {
        this.name = name;
        this.email = email;
        this.loginId = loginId;
        this.password = encodedPassword;
        this.generation = generation;
        this.part = part;
        this.role = Role.USER;
        this.termsAgreedAt = LocalDateTime.now();
        this.privacyAgreedAt = LocalDateTime.now();
    }
}
