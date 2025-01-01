package com.cupfeedeal.global.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonResponse<T> {

    private LocalDateTime timestamp;
    private int code;
    private String message;
    private T result;

    // 반환값 있는 경우
    public CommonResponse(T result, String message) {
        this.timestamp = LocalDateTime.now();
        this.code = HttpStatus.OK.value();
        this.message = message;
        this.result = result;
    }

    // 반환값 없는 경우
    public CommonResponse(String message) {
        this.timestamp = LocalDateTime.now();
        this.code = HttpStatus.OK.value();
        this.message = message;
    }
}