package com.project.questionanswerapp.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
public class CommentUpdateRequest {
    @NotNull(message = "comment text must be not null!")
    private String text;
}
