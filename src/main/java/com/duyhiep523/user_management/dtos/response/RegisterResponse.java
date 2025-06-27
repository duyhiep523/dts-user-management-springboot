package com.duyhiep523.user_management.dtos.response;

import com.duyhiep523.user_management.common.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private String userId;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private Gender gender;
    private String profilePictureUrl;
    private String role;
}
