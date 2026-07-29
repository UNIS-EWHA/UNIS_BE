package com.ewha.unis.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "page_contents")
public class PageContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_content_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PageSection section;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(name = "content_json", nullable = false)
    private String contentJson;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    private PageContent(PageSection section, String contentJson) {
        this.section = section;
        this.contentJson = contentJson;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateContent(String contentJson) {
        this.contentJson = contentJson;
        this.updatedAt = LocalDateTime.now();
    }
}
