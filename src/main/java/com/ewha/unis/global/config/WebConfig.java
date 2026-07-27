package com.ewha.unis.global.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final FileStorageProperties fileStorageProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dir = fileStorageProperties.dir();
        if (dir == null || dir.isBlank()) {
            log.warn("app.upload.dir이 설정되지 않아 정적 리소스 핸들러 등록을 건너뜁니다.");
            return;
        }

        Path path = Path.of(dir);
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            log.warn("업로드 디렉터리({})를 생성할 수 없어 정적 리소스 핸들러 등록을 건너뜁니다.", path, e);
            return;
        }

        registry.addResourceHandler("/uploads/portfolio/**")
                .addResourceLocations(path.toUri().toString());
    }
}
