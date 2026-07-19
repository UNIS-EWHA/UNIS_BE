package com.ewha.unis.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.upload")
public record FileStorageProperties(
        String dir,
        String baseUrl
) {
}
