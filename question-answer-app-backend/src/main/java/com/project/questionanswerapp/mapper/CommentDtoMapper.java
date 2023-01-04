package com.project.questionanswerapp.mapper;

import com.project.questionanswerapp.entity.Comment;
import com.project.questionanswerapp.model.response.CommentResponse;
import org.springframework.stereotype.Component;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Component
public class CommentDtoMapper {

    public CommentResponse convertToDto(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .text(comment.getText())
                .userId(comment.getUser().getId())
                .postId(comment.getPost().getId())
                .username(comment.getUser().getUsername())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
