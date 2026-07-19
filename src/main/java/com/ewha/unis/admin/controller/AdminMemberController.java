package com.ewha.unis.admin.controller;

import com.ewha.unis.admin.dto.MemberCreateResponse;
import com.ewha.unis.admin.dto.MemberListResponse;
import com.ewha.unis.admin.dto.MemberSaveRequest;
import com.ewha.unis.admin.dto.MemberStatsResponse;
import com.ewha.unis.admin.service.AdminMemberService;
import com.ewha.unis.global.response.code.SuccessCode;
import com.ewha.unis.global.response.dto.BaseResponse;
import com.ewha.unis.member.domain.MemberPart;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/members")
@RequiredArgsConstructor
public class AdminMemberController {
    private final AdminMemberService adminMemberService;

    @GetMapping("/stats")
    public BaseResponse<MemberStatsResponse> getStats() {
        return BaseResponse.ok(adminMemberService.getStats());
    }

    @GetMapping
    public BaseResponse<MemberListResponse> getMembers(
            @RequestParam(required = false) MemberPart part,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return BaseResponse.ok(adminMemberService.getMembers(part, pageable));
    }

    @PostMapping
    public BaseResponse<MemberCreateResponse> createMember(@Valid @RequestBody MemberSaveRequest request) {
        MemberCreateResponse response = adminMemberService.createMember(request);
        return BaseResponse.of(SuccessCode.CREATED, response);
    }

    @PutMapping("/{memberId}")
    public BaseResponse<Void> updateMember(
            @PathVariable Long memberId,
            @Valid @RequestBody MemberSaveRequest request) {
        adminMemberService.updateMember(memberId, request);
        return BaseResponse.ok();
    }

    @DeleteMapping("/{memberId}")
    public BaseResponse<Void> deleteMember(@PathVariable Long memberId) {
        adminMemberService.deleteMember(memberId);
        return BaseResponse.ok();
    }
}
