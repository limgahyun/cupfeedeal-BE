package com.cupfeedeal.global.exception;

import java.time.LocalDateTime;

public record ExceptionResponse (
        LocalDateTime timestamp,
        int code,
        String message
) {
    // 기본 생성자: exceptionCode만 사용하는 경우
    public ExceptionResponse(ExceptionCode exceptionCode) {
        this(LocalDateTime.now(), exceptionCode.getCode(), exceptionCode.getMessage());
    }

    // 메시지를 직접 지정하는 경우
    public ExceptionResponse(String message) {
        this(LocalDateTime.now(), ExceptionCode.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    // exceptionCode와 메시지를 동시에 사용하는 경우
    public ExceptionResponse(ExceptionCode exceptionCode, String message) {
        this(LocalDateTime.now(), exceptionCode.getCode(), message);
    }
}
