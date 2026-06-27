package com.ewha.unis.auth.controller;

import com.ewha.unis.auth.dto.LoginIdCheckResponse;
import com.ewha.unis.auth.service.AuthService;
import com.ewha.unis.global.response.code.SuccessCode;
import com.ewha.unis.global.response.dto.BaseResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login-id/check")
    public BaseResponse<LoginIdCheckResponse> checkAvailableLoginId(
            @RequestParam @NotBlank String loginId) {
        return BaseResponse.ok(authService.checkAvailableLoginId(loginId));
    }
}
