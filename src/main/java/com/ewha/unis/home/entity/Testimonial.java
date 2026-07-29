package com.ewha.unis.home.entity;

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
@Table(name = "testimonials")
public class Testimonial extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testimonial_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String role;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String content;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Builder
    private Testimonial(String name, String role, String content, int sortOrder) {
        this.name = name;
        this.role = role;
        this.content = content;
        this.sortOrder = sortOrder;
    }
}
