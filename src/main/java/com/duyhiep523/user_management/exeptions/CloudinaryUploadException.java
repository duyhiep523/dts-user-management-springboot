package com.duyhiep523.user_management.exeptions;

public class CloudinaryUploadException extends RuntimeException {
    public CloudinaryUploadException(String message) {
        super(message);
    }

    public CloudinaryUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}