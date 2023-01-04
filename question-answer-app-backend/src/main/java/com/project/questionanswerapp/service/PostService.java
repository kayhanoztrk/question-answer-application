package com.project.questionanswerapp.service;

import com.project.questionanswerapp.entity.User;
import com.project.questionanswerapp.model.response.PostResponse;
import com.project.questionanswerapp.model.response.UserResponse;
import com.project.questionanswerapp.model.request.PostCreateRequest;
import com.project.questionanswerapp.model.request.PostUpdateRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface PostService {
    List<PostResponse> findAll();

    List<PostResponse> findAllPostByUserId(Optional<Long> userId);
    PostResponse findById(Long id);
    PostResponse createPost(PostCreateRequest newPost);
    PostResponse updatePostById(Long postId, PostUpdateRequest postUpdateRequest);
    UserResponse findUserById(Long id);
    UserResponse updateUserById(Long id, User newUser);
    void deleteUserById(Long id);
    List<Long> findLastPostsById(Long userId);

}
