package com.duyhiep523.user_management.exeptions;


import com.duyhiep523.user_management.common.ResponseMessage;
import com.duyhiep523.user_management.response.ErrorResponseCustom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ErrorResponseCustom errorResponse = ErrorResponseCustom.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ResponseMessage.Exception.VALIDATION_FAILED)
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleJsonParseError(HttpMessageNotReadableException ex) {
        ErrorResponseCustom errorResponse = ErrorResponseCustom.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ResponseMessage.Exception.JSON_PARSE_ERROR)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler({BadRequestException.class,
            IllegalArgumentCustomException.class,
            MultipartException.class,
            HttpMediaTypeNotSupportedException.class,
    })
    public ResponseEntity<ErrorResponseCustom> handleIllegalArgument(Exception ex) {
        ErrorResponseCustom error = ErrorResponseCustom.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage() != null ? ex.getMessage() : ResponseMessage.Exception.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CloudinaryUploadException.class)
    public ResponseEntity<?> handleCloudinaryUploadException(CloudinaryUploadException ex) {
        ErrorResponseCustom errorResponse = ErrorResponseCustom.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponseCustom error = ErrorResponseCustom.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponseCustom errorResponse = ErrorResponseCustom.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .message(ResponseMessage.Authentication.PERMISSION_DENIED)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseCustom> handleGeneralException(Exception ex) {
        ErrorResponseCustom error = ErrorResponseCustom.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ResponseMessage.Exception.INTERNAL_SERVER_ERROR+": " + ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}