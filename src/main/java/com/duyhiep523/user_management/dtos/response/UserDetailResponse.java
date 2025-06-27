package com.duyhiep523.user_management.dtos.response;

import com.duyhiep523.user_management.common.enums.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserDetailResponse {
    private String userId;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private Gender gender;
    private String profilePictureUrl;
    private String role;
    private Boolean active;
    private Boolean isDeleted;
    private LocalDate dob;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
