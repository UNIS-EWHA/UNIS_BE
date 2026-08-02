package com.ewha.unis.global.response.code;

import com.ewha.unis.global.response.base.BaseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode implements BaseCode {
    // 400 BAD REQUEST
    INVALID_FIELD_ERROR(HttpStatus.BAD_REQUEST, "요청 필드 값이 유효하지 않습니다."),
    MISSING_PARAMETER(HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
    MISSING_HEADER(HttpStatus.BAD_REQUEST, "필수 요청 헤더가 누락되었습니다."),
    TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "요청 값 타입이 올바르지 않습니다."),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST, "요청 본문이 올바르지 않습니다."),
    EMAIL_CODE_EXPIRED(HttpStatus.BAD_REQUEST, "인증번호가 만료되었습니다."),
    EMAIL_CODE_INVALID(HttpStatus.BAD_REQUEST, "인증번호가 올바르지 않습니다."),
    EMAIL_TOKEN_INVALID(HttpStatus.BAD_REQUEST, "이메일 인증 토큰이 유효하지 않거나 만료되었습니다."),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, "PDF 또는 TXT 파일만 업로드할 수 있습니다."),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "파일 크기는 최대 100MB까지 허용됩니다."),
    EMPTY_FILE(HttpStatus.BAD_REQUEST, "업로드할 파일이 비어 있습니다."),

    // 401 UNAUTHORIZED
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않습니다."),
    REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),

    // 403 FORBIDDEN
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    RECRUITMENT_NOT_OPEN(HttpStatus.FORBIDDEN, "현재 모집 기간이 아닙니다."),

    // 404 NOT FOUND
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다."),

    // 409 CONFLICT
    DUPLICATE_LOGIN_ID(HttpStatus.CONFLICT, "이미 사용 중인 아이디입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    DUPLICATE_APPLICATION(HttpStatus.CONFLICT, "이미 해당 기수에 지원한 학번입니다."),
    LAST_SUPER_ADMIN_CANNOT_BE_DEMOTED(HttpStatus.CONFLICT, "마지막 SUPER_ADMIN 계정은 강등할 수 없습니다."),

    // 500 INTERNAL SERVER ERROR
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 문제가 발생하였습니다."),
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 발송에 실패했습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
