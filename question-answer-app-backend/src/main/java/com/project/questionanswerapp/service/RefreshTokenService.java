package com.project.questionanswerapp.service;

import com.project.questionanswerapp.entity.RefreshToken;
import com.project.questionanswerapp.entity.User;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface RefreshTokenService {
    boolean isRefreshExpired(RefreshToken token);
    String createRefreshToken(User user);

    RefreshToken findByUserId(Long userId);
}
