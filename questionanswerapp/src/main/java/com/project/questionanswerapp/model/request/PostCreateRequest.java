package com.project.questionanswerapp.model.request;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    private Long id;
    @NotNull(message = "Post content must be not null")
    private String text;
    @NotNull(message = "Post title must be not null")
    private String title;
    @NotNull(message = "UserId must be not null")
    private Long userId;
}
