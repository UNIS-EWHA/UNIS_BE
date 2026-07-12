package com.ewha.unis.project.dto;

import com.ewha.unis.project.entity.Project;
import com.ewha.unis.project.entity.ProjectTechStack;

import java.util.List;

public record ProjectDetailResponse(
        Long projectId,
        String name,
        String description,
        String thumbnailUrl,
        List<String> techStacks,
        Integer generation,
        List<ProjectMemberResponse> members,
        String githubUrl,
        String serviceUrl
) {
    public static ProjectDetailResponse from(Project project) {
        return new ProjectDetailResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getThumbnailUrl(),
                project.getTechStacks().stream().map(ProjectTechStack::getTechName).toList(),
                project.getGeneration(),
                project.getMembers().stream().map(ProjectMemberResponse::from).toList(),
                project.getGithubUrl(),
                project.getServiceUrl()
        );
    }
}
