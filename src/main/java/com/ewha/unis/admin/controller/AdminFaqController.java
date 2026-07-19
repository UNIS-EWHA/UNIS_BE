package com.ewha.unis.admin.controller;

import com.ewha.unis.about.dto.FaqResponse;
import com.ewha.unis.admin.dto.FaqCreateResponse;
import com.ewha.unis.admin.dto.FaqRequest;
import com.ewha.unis.admin.service.AdminFaqService;
import com.ewha.unis.global.response.code.SuccessCode;
import com.ewha.unis.global.response.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/faqs")
@RequiredArgsConstructor
public class AdminFaqController {
    private final AdminFaqService adminFaqService;

    @GetMapping
    public BaseResponse<List<FaqResponse>> getFaqs() {
        return BaseResponse.ok(adminFaqService.getFaqs());
    }

    @PostMapping
    public BaseResponse<FaqCreateResponse> createFaq(@Valid @RequestBody FaqRequest request) {
        FaqCreateResponse response = adminFaqService.createFaq(request);
        return BaseResponse.of(SuccessCode.CREATED, response);
    }

    @PutMapping("/{faqId}")
    public BaseResponse<Void> updateFaq(@PathVariable Long faqId, @Valid @RequestBody FaqRequest request) {
        adminFaqService.updateFaq(faqId, request);
        return BaseResponse.ok();
    }

    @DeleteMapping("/{faqId}")
    public BaseResponse<Void> deleteFaq(@PathVariable Long faqId) {
        adminFaqService.deleteFaq(faqId);
        return BaseResponse.ok();
    }
}
