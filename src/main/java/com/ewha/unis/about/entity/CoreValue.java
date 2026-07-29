package com.ewha.unis.about.entity;

import com.ewha.unis.global.entity.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "core_values")
public class CoreValue extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "core_value_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String description;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Builder
    private CoreValue(String title, String description, int sortOrder) {
        this.title = title;
        this.description = description;
        this.sortOrder = sortOrder;
    }
}
