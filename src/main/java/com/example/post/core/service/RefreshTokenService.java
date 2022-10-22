package com.example.post.core.service;

import com.example.post.api.dto.TokenRefreshResponseDto;
import com.example.post.core.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(long userId);
    Optional<RefreshToken> findByToken(String token);
    RefreshToken verifyExpiration(RefreshToken token);
    int deleteByUserId(long userId);
}
