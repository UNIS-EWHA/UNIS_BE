package com.ewha.unis.application.service;

import com.ewha.unis.application.dto.PortfolioUploadResponse;
import com.ewha.unis.global.config.FileStorageProperties;
import com.ewha.unis.global.exception.CustomException;
import com.ewha.unis.global.response.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

// TODO: 운영 환경에서는 로컬 디스크 대신 S3 등 외부 스토리지로 교체 필요
@Service
@RequiredArgsConstructor
public class PortfolioStorageService {
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("pdf", "txt");

    private final FileStorageProperties fileStorageProperties;

    public PortfolioUploadResponse store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_FILE);
        }

        String originalName = file.getOriginalFilename();
        String extension = extractExtension(originalName);
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new CustomException(ErrorCode.INVALID_FILE_TYPE);
        }

        try {
            Path dir = Path.of(fileStorageProperties.dir());
            Files.createDirectories(dir);

            String savedName = UUID.randomUUID() + "." + extension.toLowerCase();
            Path target = dir.resolve(savedName);
            file.transferTo(target);

            String fileUrl = fileStorageProperties.baseUrl() + "/" + savedName;
            return new PortfolioUploadResponse(fileUrl, originalName, file.getSize());
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    private String extractExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            throw new CustomException(ErrorCode.INVALID_FILE_TYPE);
        }
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}
