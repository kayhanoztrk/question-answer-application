package com.project.questionanswerapp.service.impl;

import com.project.questionanswerapp.entity.Comment;
import com.project.questionanswerapp.exception.CommentNotFoundException;
import com.project.questionanswerapp.mapper.CommentDtoMapper;
import com.project.questionanswerapp.mapper.PostRespMapper;
import com.project.questionanswerapp.mapper.UserRespMapper;
import com.project.questionanswerapp.model.response.CommentResponse;
import com.project.questionanswerapp.model.response.PostResponse;
import com.project.questionanswerapp.model.response.UserResponse;
import com.project.questionanswerapp.model.request.CommentCreateRequest;
import com.project.questionanswerapp.model.request.CommentUpdateRequest;
import com.project.questionanswerapp.repository.CommentRepository;
import com.project.questionanswerapp.service.CommentService;
import com.project.questionanswerapp.service.PostService;
import com.project.questionanswerapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CommentServiceImpl implements CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private CommentDtoMapper commentDtoMapper;
    private final UserService userService;
    private final PostService postService;
    private PostRespMapper postRespMapper;
    private UserRespMapper userRespMapper;


    public CommentServiceImpl(CommentRepository commentRepository,
                              CommentDtoMapper commentDtoMapper,
                              UserService userService,
                              PostService postService,
                              PostRespMapper postRespMapper,
                              UserRespMapper userRespMapper) {
        this.commentRepository = commentRepository;
        this.commentDtoMapper = commentDtoMapper;
        this.userService = userService;
        this.postService = postService;
        this.postRespMapper = postRespMapper;
        this.userRespMapper = userRespMapper;
    }

    public void setCommentDtoMapper(CommentDtoMapper commentDtoMapper) {
        this.commentDtoMapper = commentDtoMapper;
    }

    @Override
    public List<CommentResponse> findAll() {
        List<Comment> commentList = commentRepository.findAll();
        return commentList.stream()
                .map(comment -> commentDtoMapper.convertToDto(comment))
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse findById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("{} comment " +
                        "not found " + commentId));
        return commentDtoMapper.convertToDto(comment);
    }

    @Override
    public List<CommentResponse> findAllCommentsByPostIdOrUserId(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> commentList;
        if (userId.isPresent() && postId.isPresent()) {
            commentList = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            commentList = commentRepository.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            commentList = commentRepository.findByPostId(postId.get());
        } else
            commentList = commentRepository.findAll();

        return commentList.stream()
                .map(comment -> commentDtoMapper.convertToDto(comment))
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse createComment(CommentCreateRequest request) {
        UserResponse userResponse = userService.findUserById(request.getUserId());
        PostResponse postResponse = postService.findById(request.getPostId());
        logger.info("user data: {}", userResponse);
        logger.info("post data: {}", postResponse);

        if (userResponse != null && postResponse != null) {
            Comment commentToSave = new Comment();
            commentToSave.setId(request.getId());
            commentToSave.setPost(postRespMapper.convertToEntity(postResponse));
            commentToSave.setUser(userRespMapper.convertToEntity(userResponse));
            commentToSave.setText(request.getText());
            commentToSave.setCreatedAt(new Date());

            Comment savedComment = commentRepository.save(commentToSave);
            return commentDtoMapper.convertToDto(savedComment);
        }
        return null;
    }

    @Override
    public CommentResponse updateComment(Long commentId, CommentUpdateRequest request) {
        Comment toSaveComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("id:" + commentId + " not found!"));

        toSaveComment.setText(request.getText());
        commentRepository.save(toSaveComment);

        return commentDtoMapper.convertToDto(toSaveComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<Object> findUserCommentsByPostId(List<Long> postIds) {
        List<Object> commentList = commentRepository.findUserCommentsByPostId(postIds);
        return commentList;
    }
}
