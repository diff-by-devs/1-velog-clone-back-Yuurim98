package com.diffbydevs.velog_clone.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다."),
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    ACCOUNT_CONFLICT(HttpStatus.CONFLICT, "이미 사용 중인 계정입니다."),
    PASSWORD_CONFIRM_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
