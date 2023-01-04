package com.project.questionanswerapp.service.impl;

import com.project.questionanswerapp.entity.Comment;
import com.project.questionanswerapp.entity.Like;
import com.project.questionanswerapp.entity.User;
import com.project.questionanswerapp.exception.UserNotFoundException;
import com.project.questionanswerapp.mapper.UserRespMapper;
import com.project.questionanswerapp.model.response.UserResponse;
import com.project.questionanswerapp.repository.PostRepository;
import com.project.questionanswerapp.repository.UserRepository;
import com.project.questionanswerapp.service.CommentService;
import com.project.questionanswerapp.service.LikeService;
import com.project.questionanswerapp.service.PostService;
import com.project.questionanswerapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserRespMapper userRespMapper;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;

    public UserServiceImpl(UserRepository userRepository,
                           UserRespMapper userRespMapper) {
        this.userRepository = userRepository;
        this.userRespMapper = userRespMapper;
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> userRespMapper.convertToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse createUser(User newUser) {
        User user = userRepository.save(newUser);
        return userRespMapper.convertToDto(user);
    }

    @Override
    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id + "user not found!"));

        return userRespMapper.convertToDto(user);
    }

    @Override
    public UserResponse findUserByName(String username) {
         User user = userRepository.findByUsername(username);
         if(user != null)
            return userRespMapper.convertToDto(user);
         else
             return null;
    }

    @Override
    public UserResponse updateUserById(Long id, User newUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id + "user not found!"));
        //user.setUsername(newUser.getUsername());
        //user.setPassword(newUser.getPassword());
        user.setAvatar(newUser.getAvatar());

        userRepository.save(user);
        return userRespMapper.convertToDto(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Object> getUserActivity(Long userId) {
        logger.info("userIdInfo" + userId);
       List<Long> postIdList = postService.findLastPostsById(userId);
        if(postIdList.isEmpty())
            return null;
        List<Object> commentList = commentService.findUserCommentsByPostId(postIdList);
        List<Object> likeList = likeService.findUserLikesByPostId(postIdList);
        List<Object> result = new ArrayList<>();
        result.addAll(commentList);
        result.addAll(likeList);

        logger.info("postıdList" + postIdList);
        logger.info("activityList{}" + result);
        return result;
    }
}
