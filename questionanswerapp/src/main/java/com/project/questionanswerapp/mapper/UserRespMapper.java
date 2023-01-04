package com.project.questionanswerapp.mapper;

import com.project.questionanswerapp.entity.User;
import com.project.questionanswerapp.model.response.UserResponse;
import org.springframework.stereotype.Component;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Component
public class UserRespMapper {

    public UserResponse convertToDto(User user) {
        return UserResponse.builder()
                .id(user.getId() == null ? 1 : user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .avatar(user.getAvatar())
                .build();
    }

    public User convertToEntity(UserResponse userResponse) {
        User user = new User();
        user.setId(userResponse.getId());
        user.setUsername(userResponse.getUsername());
        user.setPassword(userResponse.getPassword());

        return user;
    }
}
