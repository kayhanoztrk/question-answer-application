package com.project.questionanswerapp.repository;

import com.project.questionanswerapp.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUserId(Long id);

}
