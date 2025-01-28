package com.chat.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    ROOM_NOT_FOUND("채팅방을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_PASSWORD("비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    PARTICIPANT_NOT_FOUND("참여자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INVALID_INVITE("유효하지 않은 초대입니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}