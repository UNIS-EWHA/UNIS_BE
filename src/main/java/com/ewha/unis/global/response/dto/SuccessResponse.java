package com.ewha.unis.global.response.dto;

import com.ewha.unis.global.response.base.BaseCode;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SuccessResponse<T> (
        int status,
        String code,
        String message,
        T data
) {
    public static <T> SuccessResponse<T> of(BaseCode baseCode) {
        return new SuccessResponse<>(
                baseCode.getHttpStatus().value(),
                baseCode.name(),
                baseCode.getMessage(),
                null
        );
    }

    public static <T> SuccessResponse<T> of(BaseCode baseCode, T data) {
        return new SuccessResponse<>(
                baseCode.getHttpStatus().value(),
                baseCode.name(),
                baseCode.getMessage(),
                data
        );
    }

    public static <T> SuccessResponse<T> of(BaseCode baseCode, String message) {
        return new SuccessResponse<>(
                baseCode.getHttpStatus().value(),
                baseCode.name(),
                message,
                null
        );
    }

    public static <T> SuccessResponse<T> of(BaseCode baseCode, String message, T data) {
        return new SuccessResponse<>(
                baseCode.getHttpStatus().value(),
                baseCode.name(),
                message,
                data
        );
    }
}
