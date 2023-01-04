package com.project.questionanswerapp.model.response;

import com.project.questionanswerapp.entity.Like;
import com.project.questionanswerapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String text;
    private Long userId;
    private String userName;
    private List<LikeResponse> postLikes;
}
