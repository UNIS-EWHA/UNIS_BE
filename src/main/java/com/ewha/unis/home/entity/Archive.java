package com.ewha.unis.home.entity;

import com.ewha.unis.global.entity.BaseTimeEntity;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "archives")
public class Archive extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archive_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String generation;

    @Column(nullable = false, length = 200)
    private String title;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ArchiveStatus status;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Builder
    private Archive(String generation, String title, String description,
                     ArchiveStatus status, int sortOrder) {
        this.generation = generation;
        this.title = title;
        this.description = description;
        this.status = status;
        this.sortOrder = sortOrder;
    }
}
