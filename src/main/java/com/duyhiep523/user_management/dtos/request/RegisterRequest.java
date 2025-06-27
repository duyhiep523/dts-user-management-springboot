package com.duyhiep523.user_management.dtos.request;

import com.duyhiep523.user_management.common.ResponseMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class RegisterRequest {

    @NotBlank(message = ResponseMessage.User.USERNAME_REQUIRED)
    private String username;

    @NotBlank(message = ResponseMessage.User.PASSWORD_REQUIRED)
    private String password;

    @NotBlank(message = ResponseMessage.User.CONFIRM_PASSWORD_REQUIRED)
    private String confirmPassword;

    @NotBlank(message = ResponseMessage.User.FULLNAME_REQUIRED)
    private String fullName;

    @NotBlank(message = ResponseMessage.User.EMAIL_REQUIRED)
    @Email(message = ResponseMessage.User.EMAIL_INVALID)
    private String email;

    @NotBlank(message = ResponseMessage.User.PHONE_REQUIRED)
    @Pattern(regexp = "^[0-9]+$", message = ResponseMessage.User.PHONE_ONLY_DIGITS)
    private String phone;
}
