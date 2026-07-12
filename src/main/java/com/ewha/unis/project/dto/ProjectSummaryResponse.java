package com.ewha.unis.project.dto;

import com.ewha.unis.project.entity.Project;
import com.ewha.unis.project.entity.ProjectTechStack;

import java.util.List;

public record ProjectSummaryResponse(
        Long projectId,
        String name,
        String description,
        String thumbnailUrl,
        List<String> techStacks,
        Integer generation
) {
    public static ProjectSummaryResponse from(Project project) {
        return new ProjectSummaryResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getThumbnailUrl(),
                project.getTechStacks().stream().map(ProjectTechStack::getTechName).toList(),
                project.getGeneration()
        );
    }
}
