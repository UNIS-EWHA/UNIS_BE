package com.ewha.unis.global.response.dto;

import com.ewha.unis.global.response.base.BaseCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String timestamp,
        int status,
        String errorCode,
        String message,
        String path,
        Object detail
) {
    public static ErrorResponse of(BaseCode baseCode, String path) {
        return new ErrorResponse(
                LocalDateTime.now().toString(),
                baseCode.getHttpStatus().value(),
                baseCode.name(),
                baseCode.getMessage(),
                path,
                null
        );
    }

    public static ErrorResponse of(BaseCode baseCode, String path, Object detail) {
        return new ErrorResponse(
                LocalDateTime.now().toString(),
                baseCode.getHttpStatus().value(),
                baseCode.name(),
                baseCode.getMessage(),
                path,
                detail
        );
    }
}
