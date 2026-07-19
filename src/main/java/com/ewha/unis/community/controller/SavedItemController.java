package com.ewha.unis.community.controller;

import com.ewha.unis.auth.security.CustomUserPrincipal;
import com.ewha.unis.community.dto.SavedItemListResponse;
import com.ewha.unis.community.entity.SavedTargetType;
import com.ewha.unis.community.service.SavedItemService;
import com.ewha.unis.global.response.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/saved")
@RequiredArgsConstructor
public class SavedItemController {
    private final SavedItemService savedItemService;

    @GetMapping
    public BaseResponse<SavedItemListResponse> getSavedItems(
            @RequestParam(required = false) SavedTargetType type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @AuthenticationPrincipal CustomUserPrincipal principal) {
        Pageable pageable = PageRequest.of(page, size);
        return BaseResponse.ok(savedItemService.getSavedItems(type, principal.memberId(), pageable));
    }
}
