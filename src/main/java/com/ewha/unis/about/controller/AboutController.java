package com.ewha.unis.about.controller;

import com.ewha.unis.about.dto.AboutResponse;
import com.ewha.unis.about.service.AboutService;
import com.ewha.unis.global.response.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/about")
@RequiredArgsConstructor
public class AboutController {
    private final AboutService aboutService;

    @GetMapping
    public BaseResponse<AboutResponse> getAbout() {
        return BaseResponse.ok(aboutService.getAbout());
    }
}
