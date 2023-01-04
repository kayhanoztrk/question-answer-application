package com.project.questionanswerapp.service.impl;

import com.project.questionanswerapp.entity.Comment;
import com.project.questionanswerapp.entity.Like;
import com.project.questionanswerapp.exception.LikeNotFoundException;
import com.project.questionanswerapp.mapper.LikeRespMapper;
import com.project.questionanswerapp.mapper.PostRespMapper;
import com.project.questionanswerapp.mapper.UserRespMapper;
import com.project.questionanswerapp.model.response.LikeResponse;
import com.project.questionanswerapp.model.response.PostResponse;
import com.project.questionanswerapp.model.response.UserResponse;
import com.project.questionanswerapp.model.request.LikeCreateRequest;
import com.project.questionanswerapp.model.request.LikeUpdateRequest;
import com.project.questionanswerapp.repository.LikeRepository;
import com.project.questionanswerapp.service.LikeService;
import com.project.questionanswerapp.service.PostService;
import com.project.questionanswerapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@Service
public class LikeServiceImpl implements LikeService {

    private static final Logger logger = LoggerFactory.getLogger(LikeServiceImpl.class);

    private final LikeRepository likeRepository;
    private final LikeRespMapper likeRespMapper;
    private final PostService postService;
    private final UserService userService;

    private PostRespMapper postRespMapper;
    private UserRespMapper userRespMapper;

    public LikeServiceImpl(LikeRepository likeRepository,
                           LikeRespMapper likeRespMapper,
                           PostService postService,
                           UserService userService) {
        this.likeRepository = likeRepository;
        this.likeRespMapper = likeRespMapper;
        this.postService = postService;
        this.userService = userService;
    }

    public void setPostDtoMapper(PostRespMapper postRespMapper) {
        this.postRespMapper = postRespMapper;
    }

    public void setUserDtoMapper(UserRespMapper userRespMapper) {
        this.userRespMapper = userRespMapper;
    }

    @Override
    public List<LikeResponse> findAll() {
        List<Like> likeList = likeRepository.findAll();
        return likeList.stream()
                .map(like -> likeRespMapper.convertToDto(like))
                .collect(Collectors.toList());
    }

    @Override
    public LikeResponse findById(Long id) {
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new LikeNotFoundException(id + " like not found!"));

        return likeRespMapper.convertToDto(like);
    }

    @Override
    public List<LikeResponse> findAllByPostId(Long postId) {
        List<Like> likeList = likeRepository.findByPostId(postId);
        return likeList.stream()
                .map(like -> likeRespMapper.convertToDto(like))
                .collect(Collectors.toList());
    }

    @Override
    public List<LikeResponse> findAllByUserId(Long userId) {
        List<Like> likeList = likeRepository.findByUserId(userId);

        return likeList.stream()
                .map(like -> likeRespMapper.convertToDto(like))
                .collect(Collectors.toList());
    }

    @Override
    public List<LikeResponse> findAllByUserIdAndPostId(Long userId, Long postId) {
        UserResponse userResponse = userService.findUserById(userId);
        PostResponse postResponse = postService.findById(postId);

        if (userResponse != null && postResponse != null) {
            List<Like> likeList = likeRepository.findByUserIdAndPostId(userId, postId);
            List<LikeResponse> likeResponseList = likeList.stream()
                    .map(like -> likeRespMapper.convertToDto(like))
                    .collect(Collectors.toList());
            return likeResponseList;
        }
        List<Like> likeAllList = likeRepository.findAll();
        List<LikeResponse> likeAllDtoList = likeAllList.stream()
                .map(like -> likeRespMapper.convertToDto(like))
                .collect(Collectors.toList());

        return likeAllDtoList;
    }

    @Override
    public LikeResponse createLike(LikeCreateRequest request) {
        Like toSaveLike = likeRespMapper.convertRequestToEntity(request);
        Like like = likeRepository.save(toSaveLike);
        logger.info("Like info: {}", like);
        return likeRespMapper.convertToDto(like);
    }

    @Override
    public LikeResponse updateLike(Long id, LikeUpdateRequest request) {

        Like toLike = likeRepository.findById(id)
                .orElseThrow(() -> new LikeNotFoundException(id + " like not found!"));

        PostResponse postResponse = postService.findById(request.getPostId());
        UserResponse userResponse = userService.findUserById(request.getUserId());

        toLike.setPost(postRespMapper.convertToEntity(postResponse));
        toLike.setUser(userRespMapper.convertToEntity(userResponse));
        Like savedLike = likeRepository.save(toLike);

        return likeRespMapper.convertToDto(savedLike);
    }

    @Override
    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public List<Object> findUserLikesByPostId(List<Long> postIds) {
        return likeRepository.findUserLikesByPostId(postIds);
    }
}
