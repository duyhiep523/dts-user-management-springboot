package com.duyhiep523.user_management.services;

import com.duyhiep523.user_management.common.ResponseMessage;
import com.duyhiep523.user_management.entities.RefreshToken;
import com.duyhiep523.user_management.entities.User;
import com.duyhiep523.user_management.exeptions.ResourceNotFoundException;
import com.duyhiep523.user_management.repositories.RefreshTokenRepository;
import com.duyhiep523.user_management.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;

    public RefreshToken createRefreshToken(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.User.USER_NOT_FOUND));

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new ResourceNotFoundException(ResponseMessage.Authentication.REF_TOKEN_HAS_EXPIRED);
        }
        return token;
    }
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}