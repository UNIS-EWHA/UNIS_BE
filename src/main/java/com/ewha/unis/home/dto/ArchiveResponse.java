package com.ewha.unis.home.dto;

import com.ewha.unis.home.entity.Archive;
import com.ewha.unis.home.entity.ArchiveStatus;

public record ArchiveResponse(
        Long archiveId,
        String generation,
        String title,
        String description,
        ArchiveStatus status
) {
    public static ArchiveResponse from(Archive archive) {
        return new ArchiveResponse(
                archive.getId(),
                archive.getGeneration(),
                archive.getTitle(),
                archive.getDescription(),
                archive.getStatus()
        );
    }
}
