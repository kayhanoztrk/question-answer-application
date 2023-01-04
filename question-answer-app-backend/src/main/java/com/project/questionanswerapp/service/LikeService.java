package com.project.questionanswerapp.service;

import com.project.questionanswerapp.entity.Comment;
import com.project.questionanswerapp.entity.Like;
import com.project.questionanswerapp.model.response.LikeResponse;
import com.project.questionanswerapp.model.request.LikeCreateRequest;
import com.project.questionanswerapp.model.request.LikeUpdateRequest;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public interface LikeService {
    List<LikeResponse> findAll();

    LikeResponse findById(Long id);

    List<LikeResponse> findAllByPostId(Long postId);

    List<LikeResponse> findAllByUserId(Long userId);

    List<LikeResponse> findAllByUserIdAndPostId(Long userId,
                                                Long postId);

    LikeResponse createLike(LikeCreateRequest request);

    LikeResponse updateLike(Long id, LikeUpdateRequest request);

    void deleteLike(Long id);

    List<Object> findUserLikesByPostId(List<Long> postIds);
}
