package com.project.questionanswerapp.mapper;

import com.project.questionanswerapp.entity.Like;
import com.project.questionanswerapp.entity.Post;
import com.project.questionanswerapp.entity.User;
import com.project.questionanswerapp.model.response.LikeResponse;
import com.project.questionanswerapp.model.request.LikeCreateRequest;
import org.springframework.stereotype.Component;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Component
public class LikeRespMapper {

    public LikeResponse convertToDto(Like like) {
        return LikeResponse.builder()
                .id(like.getId())
                .postId(like.getPost().getId())
                .userId(like.getUser().getId())
                .build();
    }

    public Like convertToEntity(LikeResponse likeResponse) {
        Like like = new Like();
        User user = new User();
        Post post = new Post();

        user.setId(likeResponse.getPostId());
        post.setId(likeResponse.getPostId());

        like.setId(likeResponse.getId());
        like.setUser(user);
        like.setPost(post);
        return like;
    }

    public Like convertRequestToEntity(LikeCreateRequest request) {
        Like toSave = new Like();
        toSave.setId(request.getId());

        User user = new User();
        user.setId(request.getUserId());

        Post post = new Post();
        post.setId(request.getPostId());

        toSave.setUser(user);
        toSave.setPost(post);

        return toSave;
    }
}
