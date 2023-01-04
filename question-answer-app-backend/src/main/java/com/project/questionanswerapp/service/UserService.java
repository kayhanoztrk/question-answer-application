package com.project.questionanswerapp.service;

import com.project.questionanswerapp.entity.User;
import com.project.questionanswerapp.model.response.UserResponse;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface UserService {
    List<UserResponse> findAll();

    UserResponse createUser(User newUser);
    UserResponse findUserById(Long id);
    UserResponse findUserByName(String username);
    UserResponse updateUserById(Long id, User newUser);
    void deleteUserById(Long id);

    List<Object> getUserActivity(Long userId);
}
