package com.project.questionanswerapp.mapper;

import com.project.questionanswerapp.entity.Post;
import com.project.questionanswerapp.entity.User;
import com.project.questionanswerapp.model.response.PostResponse;
import com.project.questionanswerapp.model.request.PostCreateRequest;
import org.springframework.stereotype.Component;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Component
public class PostRespMapper {

    public PostResponse convertToDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .userId(post.getUser().getId())
                .userName(post.getUser().getUsername())
                .build();
    }

    public Post convertToEntity(PostCreateRequest post,
                                User user) {
        Post toSave = new Post();
        toSave.setId(post.getId());
        toSave.setTitle(post.getTitle());
        toSave.setText(post.getText());
        toSave.setUser(user);

        return toSave;
    }

    public Post convertToEntity(PostResponse postResponse) {
        Post post = new Post();
        post.setId(postResponse.getId());
        post.setTitle(postResponse.getTitle());
        post.setText(postResponse.getText());

        return post;
    }

    public Post convertToRequest(PostCreateRequest postCreateRequest) {
        Post post = new Post();
        post.setId(postCreateRequest.getId());
        post.setTitle(postCreateRequest.getTitle());
        post.setText(postCreateRequest.getText());

        User user = new User();
        user.setId(postCreateRequest.getId());

        post.setUser(user);
        return post;
    }
}
