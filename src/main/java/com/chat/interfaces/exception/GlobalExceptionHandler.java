package com.chat.interfaces.exception;

import com.chat.application.exception.ChatException;
import com.chat.application.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ChatException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleChatException(ChatException ex) {
        return Mono.just(
                ResponseEntity
                        .status(ex.getErrorCode().getStatus())
                        .body(new ErrorResponse(ex.getErrorCode(), ex.getMessage()))
        );
    }

    private record ErrorResponse(ErrorCode errorCode, String message) {}
}