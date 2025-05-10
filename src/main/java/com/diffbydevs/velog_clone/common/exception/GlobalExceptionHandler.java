package com.diffbydevs.velog_clone.common.exception;

import com.diffbydevs.velog_clone.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomException(CustomException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity
            .status(errorCode.getHttpStatus())
            .body(ApiResponse.error(errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(
        MethodArgumentNotValidException exception) {

        FieldError firstError = exception.getFieldErrors().get(0);
        String message = String.format("{%s} %s", firstError.getField(), firstError.getDefaultMessage());

        return ResponseEntity
            .status(ErrorCode.VALIDATION_FAILED.getHttpStatus())
            .body(ApiResponse.error(ErrorCode.VALIDATION_FAILED.getHttpStatus(), message));
    }

}
