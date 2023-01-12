package com.project.questionanswerapp.service.impl;

import com.project.questionanswerapp.entity.Like;
import com.project.questionanswerapp.entity.Post;
import com.project.questionanswerapp.entity.User;
import com.project.questionanswerapp.exception.PostNotFoundException;
import com.project.questionanswerapp.exception.UserNotFoundException;
import com.project.questionanswerapp.mapper.PostRespMapper;
import com.project.questionanswerapp.mapper.UserRespMapper;
import com.project.questionanswerapp.model.response.LikeResponse;
import com.project.questionanswerapp.model.response.PostResponse;
import com.project.questionanswerapp.model.response.UserResponse;
import com.project.questionanswerapp.model.request.PostCreateRequest;
import com.project.questionanswerapp.model.request.PostUpdateRequest;
import com.project.questionanswerapp.repository.PostRepository;
import com.project.questionanswerapp.service.LikeService;
import com.project.questionanswerapp.service.PostService;
import com.project.questionanswerapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostRespMapper postRespMapper;
    @Autowired
    private UserRespMapper userRespMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;


    @Override
    public List<PostResponse> findAll() {
        List<Post> postList = postRepository.findAll();
        return postList.stream()
                .map(post -> {
                    PostResponse response = postRespMapper.convertToDto(post);
                    response.setPostLikes(likeService.findAllByPostId(post.getId()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> findAllPostByUserId(Optional<Long> userId) {
        if (userId.isPresent()) {
            List<Post> postList = postRepository.findByUserId(userId.get());
            return postList.stream().map(post -> {
                PostResponse postResp = postRespMapper.convertToDto(post);
                List<LikeResponse> likeList = likeService.findAllByPostId(post.getId());
                postResp.setPostLikes(likeList);
                return postResp;
            }).collect(Collectors.toList());
        }

        return postRepository.findAll()
                .stream()
                .map(post -> postRespMapper.convertToDto(post))
                .collect(Collectors.toList());
    }

    @Override
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        List<LikeResponse> likes = likeService.findAllByPostId(post.getId());

        PostResponse postResponse = postRespMapper.convertToDto(post);
        postResponse.setPostLikes(likes);
        return postResponse;
    }

    @Override
    public PostResponse createPost(PostCreateRequest newPost) {
        UserResponse userResponse = userService.findUserById(newPost.getUserId());
        if (userResponse == null)
            return null;

        Post post = postRespMapper.convertToEntity(newPost,
                userRespMapper.convertToEntity(userResponse));
        post.setCreatedAt(new Date());

        Post savedPost = postRepository.save(post);
        return postRespMapper.convertToDto(savedPost);
    }

    @Override
    public PostResponse updatePostById(Long postId, PostUpdateRequest postUpdateRequest) {
        Post toPost = postRepository.findById(postId)
                .orElseThrow(() -> new UserNotFoundException("USER NOT FOUND " + postId));
        toPost.setTitle(postUpdateRequest.getTitle());
        toPost.setText(postUpdateRequest.getText());
        Post post = postRepository.save(toPost);

        return postRespMapper.convertToDto(post);
    }

    @Override
    public UserResponse findUserById(Long id) {
        return null;
    }

    @Override
    public UserResponse updateUserById(Long id, User newUser) {
        return null;
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public List<Long> findLastPostsById(Long userId) {
        return postRepository.findLastPostsById(userId);
    }
}
