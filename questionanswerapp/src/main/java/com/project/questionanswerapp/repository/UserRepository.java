package com.project.questionanswerapp.repository;

import com.project.questionanswerapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
