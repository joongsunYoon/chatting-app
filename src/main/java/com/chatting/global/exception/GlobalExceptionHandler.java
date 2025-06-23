package com.chatting.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse> handleCustomException(CustomException ex) {
        return ResponseEntity.status(ex.getErrorCode().getStatus())
                .body(new ApiResponse(ex.getErrorCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
        return ResponseEntity.status(500)
                .body(new ApiResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @Getter
    @AllArgsConstructor
    public static class ApiResponse<T> {
        private int status;
        private String message;
        private T data;

        public ApiResponse(ErrorCode errorCode) {
            this.status = errorCode.getStatus();
            this.message = errorCode.getMessage();
            this.data = null;
        }
    }
}
