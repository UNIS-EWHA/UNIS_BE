package com.ewha.unis.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final FileStorageProperties fileStorageProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = Path.of(fileStorageProperties.dir()).toUri().toString();
        registry.addResourceHandler("/uploads/portfolio/**")
                .addResourceLocations(location);
    }
}
