package com.ewha.unis.global.response.base;

import org.springframework.http.HttpStatus;

public interface BaseCode {
    HttpStatus getHttpStatus();
    String getMessage();
    String name();
}
