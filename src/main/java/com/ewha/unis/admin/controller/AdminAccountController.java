package com.ewha.unis.admin.controller;

import com.ewha.unis.admin.dto.AdminAccountGrantRequest;
import com.ewha.unis.admin.dto.AdminAccountResponse;
import com.ewha.unis.admin.service.AdminAccountService;
import com.ewha.unis.global.response.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/accounts")
@RequiredArgsConstructor
public class AdminAccountController {
    private final AdminAccountService adminAccountService;

    @GetMapping
    public BaseResponse<List<AdminAccountResponse>> getAccounts() {
        return BaseResponse.ok(adminAccountService.getAccounts());
    }

    @PostMapping
    public BaseResponse<Void> grantAdmin(@Valid @RequestBody AdminAccountGrantRequest request) {
        adminAccountService.grantAdmin(request);
        return BaseResponse.ok();
    }

    @DeleteMapping("/{accountId}")
    public BaseResponse<Void> revokeAdmin(@PathVariable Long accountId) {
        adminAccountService.revokeAdmin(accountId);
        return BaseResponse.ok();
    }
}
