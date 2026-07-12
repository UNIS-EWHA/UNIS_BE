package com.ewha.unis.admin.controller;

import com.ewha.unis.admin.dto.RecruitSettingsRequest;
import com.ewha.unis.admin.dto.RecruitSettingsResponse;
import com.ewha.unis.admin.service.RecruitSettingsService;
import com.ewha.unis.global.response.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/recruit/settings")
@RequiredArgsConstructor
public class AdminRecruitSettingsController {
    private final RecruitSettingsService recruitSettingsService;

    @GetMapping
    public BaseResponse<RecruitSettingsResponse> getSettings() {
        return BaseResponse.ok(recruitSettingsService.getSettings());
    }

    @PutMapping
    public BaseResponse<Void> saveSettings(@Valid @RequestBody RecruitSettingsRequest request) {
        recruitSettingsService.saveSettings(request);
        return BaseResponse.ok();
    }
}
