package com.ewha.unis.auth.controller;

import com.ewha.unis.auth.dto.LoginIdCheckResponse;
import com.ewha.unis.auth.dto.SignUpRequest;
import com.ewha.unis.auth.service.AuthService;
import com.ewha.unis.global.response.code.SuccessCode;
import com.ewha.unis.global.response.dto.BaseResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/signup")
    public BaseResponse<Void> signUp(
            @Valid @RequestBody SignUpRequest request) {
        authService.signUp(request);
        return BaseResponse.created();
    }
}
