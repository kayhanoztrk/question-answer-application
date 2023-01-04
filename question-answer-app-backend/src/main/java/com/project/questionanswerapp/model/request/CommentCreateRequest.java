package com.project.questionanswerapp.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
public class CommentCreateRequest {
    private Long id;
    @NotNull(message = "postId must be not null!")
    private Long postId;
    @NotNull(message = "userId mut be not null!")
    private Long userId;
    @NotNull(message = "post text must be not null!")
    private String text;
}
