package com.ewha.unis.application.dto;

public record PortfolioUploadResponse(
        String fileUrl,
        String fileName,
        long fileSize
) {
}
