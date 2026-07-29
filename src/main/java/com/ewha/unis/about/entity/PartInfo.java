package com.ewha.unis.about.entity;

import com.ewha.unis.global.entity.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "part_infos")
public class PartInfo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_info_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String description;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @OneToMany(mappedBy = "partInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartTag> tags = new ArrayList<>();

    @Builder
    private PartInfo(String name, String description, int sortOrder) {
        this.name = name;
        this.description = description;
        this.sortOrder = sortOrder;
    }

    public void addTag(PartTag tag) {
        tags.add(tag);
        tag.assignPartInfo(this);
    }
}
