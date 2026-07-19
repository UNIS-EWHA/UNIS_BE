package com.ewha.unis.activity.controller;

import com.ewha.unis.activity.dto.ActivityResponse;
import com.ewha.unis.activity.service.ActivityService;
import com.ewha.unis.global.response.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping
    public BaseResponse<ActivityResponse> getActivity() {
        return BaseResponse.ok(activityService.getActivity());
    }
}
