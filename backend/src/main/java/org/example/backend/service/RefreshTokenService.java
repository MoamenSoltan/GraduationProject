package org.example.backend.service;

import org.example.backend.entity.RefreshToken;
import org.example.backend.entity.User;
import org.example.backend.exception.ResourceNotFound;
import org.example.backend.repository.RefreshTokenRepository;
import org.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${application.security.jwt.refresh-token-expiration}")
    private long VALID_TOKEN;
    private final RefreshTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository tokenRepository, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String email)
    {
        User user = userRepository.findUsersByEmail(email);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(VALID_TOKEN));
        refreshToken.setRevoked(false);
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken)
    {
        if(refreshToken.getExpiryDate().compareTo(Instant.now())<0)
        {
            refreshTokenRepository.delete(refreshToken);
            throw new ResourceNotFound("refresh token", "expired date",refreshToken.getExpiryDate());
        }

        if (refreshToken.isRevoked()) {
            throw new ResourceNotFound("refresh token", "status", "revoked");
        }

        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String refresh)
    {
        return refreshTokenRepository.findByToken(refresh);
    }
}
