package com.ewha.unis.auth.controller;

import com.ewha.unis.auth.dto.*;
import com.ewha.unis.auth.service.AuthService;
import com.ewha.unis.global.response.dto.BaseResponse;
import com.ewha.unis.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;
    private final CookieUtil cookieUtil;

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

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                             HttpServletResponse response) {
        LoginResult result = authService.login(request);
        response.addHeader(HttpHeaders.SET_COOKIE, cookieUtil.create(result.refreshToken(),
                result.refreshTokenValidity()).toString());
        return BaseResponse.ok(new LoginResponse(result.accessToken(), result.role()));
    }

    @PostMapping("/reissue")
    public BaseResponse<LoginResponse> reissue(
            @CookieValue(name = CookieUtil.REFRESH_TOKEN, required = false) String refreshToken,
            HttpServletResponse response) {
        LoginResult result = authService.reissue(refreshToken);
        response.addHeader(HttpHeaders.SET_COOKIE,
                cookieUtil.create(result.refreshToken(), result.refreshTokenValidity()).toString());
        return BaseResponse.ok(new LoginResponse(result.accessToken(), result.role()));
    }
}
