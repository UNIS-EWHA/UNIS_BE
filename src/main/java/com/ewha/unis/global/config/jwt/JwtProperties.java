package com.ewha.unis.global.config.jwt;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secret,
        long accessTokenValidity,
        long refreshTokenValidity,
        long refreshTokenValidityShort
) {
    @PostConstruct
    public void logProperties() {
        log.info("JwtProperties bound: accessTokenValidity={}, refreshTokenValidity={}, refreshTokenValidityShort={}",
                accessTokenValidity, refreshTokenValidity, refreshTokenValidityShort);
    }
}
