package com.project.questionanswerapp.model.request;

import lombok.Data;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
public class LikeCreateRequest {
    private Long id;
    private Long postId;
    private Long userId;
}
