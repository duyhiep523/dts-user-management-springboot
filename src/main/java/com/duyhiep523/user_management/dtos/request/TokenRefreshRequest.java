package com.duyhiep523.user_management.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshRequest {
    private String refreshToken;
}
