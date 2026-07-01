package com.ewha.unis.global.util;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class CookieUtil {
    public static final String REFRESH_TOKEN = "refreshToken";

    public ResponseCookie create(String value, long validityMs) {
        return ResponseCookie.from(REFRESH_TOKEN, value)
                .httpOnly(true)
                .secure(false) // TODO: HTTPS 세팅 후 변경
                .sameSite("Lax")
                .path("/api/v1/auth")
                .maxAge(Duration.ofMillis(validityMs))
                .build();
    }

    public ResponseCookie expire() {
        return ResponseCookie.from(REFRESH_TOKEN, "")
                .httpOnly(true)
                .secure(false) // TODO: HTTPS 세팅 후 변경
                .sameSite("Lax")
                .path("/api/v1/auth")
                .maxAge(0)
                .build();
    }
}
