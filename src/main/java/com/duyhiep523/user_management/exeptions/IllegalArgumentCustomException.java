package com.duyhiep523.user_management.exeptions;

public class IllegalArgumentCustomException extends IllegalArgumentException {
    public IllegalArgumentCustomException(String message) {
        super(message);
    }

    public IllegalArgumentCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgumentCustomException(Throwable cause) {
        super(cause);
    }
}