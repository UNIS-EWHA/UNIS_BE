package com.ewha.unis.project.dto;

import com.ewha.unis.project.entity.Project;
import org.springframework.data.domain.Page;

import java.util.List;

public record ProjectListResponse(
        List<ProjectSummaryResponse> projects,
        long totalCount,
        boolean hasNext
) {
    public static ProjectListResponse from(Page<Project> page) {
        List<ProjectSummaryResponse> projects = page.getContent().stream()
                .map(ProjectSummaryResponse::from)
                .toList();
        return new ProjectListResponse(projects, page.getTotalElements(), page.hasNext());
    }
}
