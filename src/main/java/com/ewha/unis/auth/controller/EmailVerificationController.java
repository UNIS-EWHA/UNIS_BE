package com.ewha.unis.auth.controller;

import com.ewha.unis.auth.dto.EmailSendRequest;
import com.ewha.unis.auth.dto.EmailVerifyRequest;
import com.ewha.unis.auth.dto.EmailVerifyResponse;
import com.ewha.unis.auth.service.EmailVerificationService;
import com.ewha.unis.global.response.code.SuccessCode;
import com.ewha.unis.global.response.dto.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/email")
@RequiredArgsConstructor
public class EmailVerificationController {
    private final EmailVerificationService emailVerificationService;

    @PostMapping("/send")
    public BaseResponse<Void> sendCode(
            @RequestBody @Valid EmailSendRequest request) {
        emailVerificationService.sendCode(request.email());
        return BaseResponse.ok();
    }

    @PostMapping("/verify")
    public BaseResponse<EmailVerifyResponse> verifyCode(
            @RequestBody @Valid EmailVerifyRequest request) {
        return BaseResponse.ok(emailVerificationService.verifyCode(request.email(), request.code()));
    }
}
