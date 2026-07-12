package com.ewha.unis.project.controller;

import com.ewha.unis.global.response.dto.BaseResponse;
import com.ewha.unis.project.dto.ProjectDetailResponse;
import com.ewha.unis.project.dto.ProjectListResponse;
import com.ewha.unis.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public BaseResponse<ProjectListResponse> getProjects(
            @RequestParam(required = false) Integer generation,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return BaseResponse.ok(projectService.getProjects(generation, keyword, pageable));
    }

    @GetMapping("/{projectId}")
    public BaseResponse<ProjectDetailResponse> getProjectDetail(
            @PathVariable Long projectId) {
        return BaseResponse.ok(projectService.getProjectDetail(projectId));
    }
}
