package com.duyhiep523.user_management.dtos.request;

import com.duyhiep523.user_management.common.ResponseMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = ResponseMessage.User.USERNAME_REQUIRED)
    private String username;

    @NotBlank(message = ResponseMessage.User.PASSWORD_REQUIRED)
    private String password;
}