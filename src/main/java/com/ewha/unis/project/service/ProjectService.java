package com.ewha.unis.project.service;

import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import com.ewha.unis.project.dto.ProjectDetailResponse;
import com.ewha.unis.project.dto.ProjectListResponse;
import com.ewha.unis.project.entity.Project;
import com.ewha.unis.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectListResponse getProjects(Integer generation, String keyword, Pageable pageable) {
        Page<Project> projects = projectRepository.search(generation, keyword, pageable);
        return ProjectListResponse.from(projects);
    }

    @Transactional
    public ProjectDetailResponse getProjectDetail(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESOURCE_NOT_FOUND));
        project.increaseViewCount();
        return ProjectDetailResponse.from(project);
    }
}
