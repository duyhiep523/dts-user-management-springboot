package com.duyhiep523.user_management.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TokenRefreshResponse {
    private String accessToken;
    private String refreshToken;
}
