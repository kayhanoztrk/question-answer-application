package com.project.questionanswerapp.service.impl;

import com.project.questionanswerapp.entity.RefreshToken;
import com.project.questionanswerapp.entity.User;
import com.project.questionanswerapp.repository.RefreshTokenRepository;
import com.project.questionanswerapp.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${questapp.app.refreshtoken.expires.in}")
    private Long expireSeconds;
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public boolean isRefreshExpired(RefreshToken token) {
        return token.getExpiryDate().before(new Date());
    }

    @Override
    public String createRefreshToken(User user) {
        RefreshToken token = refreshTokenRepository.findByUserId(user.getId());
        if (token == null) {
            token = new RefreshToken();
            token.setUser(user);
        }
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Date.from(Instant.now().plusSeconds(expireSeconds)));
        refreshTokenRepository.save(token);
        return token.getToken();
    }

    @Override
    public RefreshToken findByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }
}
