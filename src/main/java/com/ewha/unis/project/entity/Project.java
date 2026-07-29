package com.ewha.unis.project.entity;

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
@Table(name = "projects")
public class Project extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String description;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(nullable = false)
    private Integer generation;

    @Column(name = "github_url", length = 500)
    private String githubUrl;

    @Column(name = "service_url", length = 500)
    private String serviceUrl;

    @Column(name = "view_count", nullable = false)
    private int viewCount;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectTechStack> techStacks = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMember> members = new ArrayList<>();

    @Builder
    private Project(String name, String description, String thumbnailUrl, Integer generation,
                     String githubUrl, String serviceUrl) {
        this.name = name;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.generation = generation;
        this.githubUrl = githubUrl;
        this.serviceUrl = serviceUrl;
        this.viewCount = 0;
    }

    public void addTechStack(ProjectTechStack techStack) {
        techStacks.add(techStack);
        techStack.assignProject(this);
    }

    public void addMember(ProjectMember member) {
        members.add(member);
        member.assignProject(this);
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}
