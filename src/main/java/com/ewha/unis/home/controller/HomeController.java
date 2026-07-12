package com.ewha.unis.home.controller;

import com.ewha.unis.global.response.dto.BaseResponse;
import com.ewha.unis.home.dto.ArchiveResponse;
import com.ewha.unis.home.dto.HomeStatsResponse;
import com.ewha.unis.home.dto.TestimonialResponse;
import com.ewha.unis.home.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/home")
@RequiredArgsConstructor
public class HomeController {
    private final HomeService homeService;

    @GetMapping("/stats")
    public BaseResponse<HomeStatsResponse> getStats() {
        return BaseResponse.ok(homeService.getStats());
    }

    @GetMapping("/archive")
    public BaseResponse<List<ArchiveResponse>> getArchives() {
        return BaseResponse.ok(homeService.getArchives());
    }

    @GetMapping("/testimonials")
    public BaseResponse<List<TestimonialResponse>> getTestimonials() {
        return BaseResponse.ok(homeService.getTestimonials());
    }
}
