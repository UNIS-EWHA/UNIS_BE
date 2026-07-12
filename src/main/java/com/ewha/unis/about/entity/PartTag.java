package com.ewha.unis.about.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "part_tags")
public class PartTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "part_info_id", nullable = false)
    private PartInfo partInfo;

    @Column(name = "tag_name", nullable = false, length = 50)
    private String tagName;

    @Builder
    private PartTag(String tagName) {
        this.tagName = tagName;
    }

    void assignPartInfo(PartInfo partInfo) {
        this.partInfo = partInfo;
    }
}
