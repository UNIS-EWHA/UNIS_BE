package com.ewha.unis.activity.dto;

import com.ewha.unis.activity.entity.Program;

public record ProgramResponse(
        Long programId,
        String title,
        String description,
        String imageUrl
) {
    public static ProgramResponse from(Program program) {
        return new ProgramResponse(program.getId(), program.getTitle(), program.getDescription(), program.getImageUrl());
    }
}
