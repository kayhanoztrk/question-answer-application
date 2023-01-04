package com.project.questionanswerapp.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponse {

    private Long id;
    private Long postId;
    private Long userId;
}
