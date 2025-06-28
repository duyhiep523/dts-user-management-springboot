package com.duyhiep523.user_management.repositories;

import com.duyhiep523.user_management.entities.RefreshToken;
import com.duyhiep523.user_management.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
    int deleteByUser(User user);
}
