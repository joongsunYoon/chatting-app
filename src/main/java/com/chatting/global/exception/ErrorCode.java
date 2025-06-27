package com.chatting.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_TOKEN(401, "잘못된 인증 토큰입니다."),
    USER_NOT_FOUND(404, "잘못된 userId입니다."),
    DATA_NOT_FOUND(404, "대상이 존재하지 않습니다."),
    INVALID_DATA(400, "잘못된 값입니다."),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String message;
}