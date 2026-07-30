package com.ewha.unis.admin.controller;

import com.ewha.unis.about.dto.AboutResponse;
import com.ewha.unis.about.service.AboutService;
import com.ewha.unis.admin.dto.AboutIntroEditRequest;
import com.ewha.unis.admin.dto.CoreValuesEditRequest;
import com.ewha.unis.admin.dto.ExperienceEditRequest;
import com.ewha.unis.admin.dto.HeroEditRequest;
import com.ewha.unis.admin.dto.PartsEditRequest;
import com.ewha.unis.admin.dto.PhotosEditRequest;
import com.ewha.unis.admin.service.PageContentService;
import com.ewha.unis.global.response.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/content")
@RequiredArgsConstructor
public class AdminContentController {
    private final AboutService aboutService;
    private final PageContentService pageContentService;

    @GetMapping("/about")
    public BaseResponse<AboutResponse> getAboutContent() {
        return BaseResponse.ok(aboutService.getAbout());
    }

    @PutMapping("/hero")
    public BaseResponse<Void> updateHero(@Valid @RequestBody HeroEditRequest request) {
        pageContentService.updateHero(request);
        return BaseResponse.ok();
    }

    @PutMapping("/about/intro")
    public BaseResponse<Void> updateAboutIntro(@Valid @RequestBody AboutIntroEditRequest request) {
        pageContentService.updateAboutIntro(request);
        return BaseResponse.ok();
    }

    @PutMapping("/about/values")
    public BaseResponse<Void> updateCoreValues(@Valid @RequestBody CoreValuesEditRequest request) {
        pageContentService.updateCoreValues(request);
        return BaseResponse.ok();
    }

    @PutMapping("/about/parts")
    public BaseResponse<Void> updateParts(@Valid @RequestBody PartsEditRequest request) {
        pageContentService.updateParts(request);
        return BaseResponse.ok();
    }

    @PutMapping("/about/photos")
    public BaseResponse<Void> updatePhotos(@Valid @RequestBody PhotosEditRequest request) {
        pageContentService.updatePhotos(request);
        return BaseResponse.ok();
    }

    @PutMapping("/about/experience")
    public BaseResponse<Void> updateExperience(@Valid @RequestBody ExperienceEditRequest request) {
        pageContentService.updateExperience(request);
        return BaseResponse.ok();
    }
}
