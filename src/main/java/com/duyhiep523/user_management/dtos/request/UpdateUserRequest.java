package com.duyhiep523.user_management.dtos.request;

import com.duyhiep523.user_management.annotation.ValidEnum;
import com.duyhiep523.user_management.common.ResponseMessage;
import com.duyhiep523.user_management.common.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {

    @NotBlank(message = ResponseMessage.User.USERNAME_REQUIRED)
    private String fullName;

    @Email(message = ResponseMessage.User.EMAIL_INVALID)
    private String email;

    @NotBlank(message = ResponseMessage.User.PHONE_REQUIRED)
    @Pattern(regexp = "^[0-9]+$", message = ResponseMessage.User.PHONE_ONLY_DIGITS)
    private String phone;

    @Past(message = ResponseMessage.User.DOB_PAST)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @ValidEnum(enumClass = Gender.class, message = ResponseMessage.User.GENDER_INVALID)
    private String gender;
}