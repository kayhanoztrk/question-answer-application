package com.project.questionanswerapp.service;

import com.project.questionanswerapp.entity.Comment;
import com.project.questionanswerapp.model.response.CommentResponse;
import com.project.questionanswerapp.model.request.CommentCreateRequest;
import com.project.questionanswerapp.model.request.CommentUpdateRequest;

import java.util.List;
import java.util.Optional;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface CommentService {
    List<CommentResponse> findAll();
    CommentResponse findById(Long commentId);
    List<CommentResponse> findAllCommentsByPostIdOrUserId(Optional<Long> userId, Optional<Long> postId);

    CommentResponse createComment(CommentCreateRequest request);

    CommentResponse updateComment(Long commentId, CommentUpdateRequest request);

    void deleteComment(Long commentId);
    List<Object> findUserCommentsByPostId(List<Long> postIds);

}
