package com.ewha.unis.global.exception;

import com.ewha.unis.global.response.base.BaseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final BaseCode baseCode;

    public CustomException(BaseCode baseCode) {
        super(baseCode.getMessage());
        this.baseCode = baseCode;
    }
}
