package com.project.questionanswerapp.model.response;

import lombok.Data;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
public class AuthResponse {
    String message;
    Long userId;
    String accessToken;
    String refreshToken;
}
