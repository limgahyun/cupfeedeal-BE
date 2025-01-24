package com.cupfeedeal.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    // 1000: Success Case

    // 2000: Common Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 2000, "서버 에러가 발생하였습니다. 관리자에게 문의해 주세요."),
    NOT_FOUND_EXCEPTION(HttpStatus.NOT_FOUND, 2001, "존재하지 않는 리소스입니다."),
    INVALID_VALUE_EXCEPTION(HttpStatus.BAD_REQUEST, 2002, "올바르지 않은 요청 값입니다."),
    UNAUTHORIZED_EXCEPTION(HttpStatus.UNAUTHORIZED, 2003, "권한이 없는 요청입니다."),
    ALREADY_DELETE_EXCEPTION(HttpStatus.BAD_REQUEST, 2004, "이미 삭제된 리소스입니다."),
    FORBIDDEN_EXCEPTION(HttpStatus.FORBIDDEN, 2005, "인가되지 않는 요청입니다."),
    ALREADY_EXIST_EXCEPTION(HttpStatus.BAD_REQUEST, 2006, "이미 존재하는 리소스입니다."),
    INVALID_SORT_EXCEPTION(HttpStatus.BAD_REQUEST, 2007, "올바르지 않은 정렬 값입니다."),
    BAD_REQUEST_ERROR(HttpStatus.BAD_REQUEST, 2008, "잘못된 요청입니다."),

    // 3000: Auth/User Error
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 3000, "해당 유저를 찾을 수 없습니다."),

    // 4000: Cafe error
    NOT_FOUND_CAFE(HttpStatus.NOT_FOUND, 4000, "해당 cafe를 찾을 수 없습니다."),
    NOT_FOUND_CAFE_LIKE(HttpStatus.NOT_FOUND, 4001, "해당 cafe 좋아요를 찾을 수 없습니다."),
    ALREADY_EXIST_LIKE(HttpStatus.NOT_FOUND,4002, "이미 해당 좋아요가 있습니다."),

    // 5000: Subscription error
    NOT_FOUND_SUBSCRIPTION(HttpStatus.NOT_FOUND, 5000, "구독권을 찾을 수 없습니다."),
    NOT_FOUND_CAFE_SUBSCRIPTION_TYPE(HttpStatus.NOT_FOUND, 5001, "카페에 해당 구독권을 찾을 수 없습니다."),


    // 6000: user subscription error
    NOT_FOUND_USER_SUBSCRIPTION(HttpStatus.NOT_FOUND, 6000, "구독 중인 해당 구독권을 찾을 수 없습니다."),
    ALREADY_USED_SUBSCRIPTION(HttpStatus.BAD_REQUEST, 6001, "해당 구독권을 이미 사용하였습니다."),
    ALREADY_EXPIRED_SUBSCRIPTION(HttpStatus.BAD_REQUEST, 6002, "만료된 구독권입니다."),
    BEFORE_SUBSCRIPTION_START(HttpStatus.BAD_REQUEST, 6003, "구독권 사용기간 전입니다."),
    ALREADY_FULL_SUBSCRIPTION(HttpStatus.BAD_REQUEST, 6004, "이미 구독권을 3개 가지고 있어, 더 이상 구독할 수 없습니다.");

    //7000: [임의] Error

    ;

    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

}
