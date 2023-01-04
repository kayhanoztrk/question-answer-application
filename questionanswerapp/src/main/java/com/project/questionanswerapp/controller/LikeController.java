package com.project.questionanswerapp.controller;

import com.project.questionanswerapp.mapper.LikeRespMapper;
import com.project.questionanswerapp.model.response.LikeResponse;
import com.project.questionanswerapp.model.request.LikeCreateRequest;
import com.project.questionanswerapp.model.request.LikeUpdateRequest;
import com.project.questionanswerapp.service.LikeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@RestController
@RequestMapping("/api/v0/likes")
public class LikeController {
    private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

    private final LikeService likeService;
    private final LikeRespMapper likeRespMapper;

    public LikeController(LikeService likeService,
                          LikeRespMapper likeRespMapper) {
        this.likeService = likeService;
        this.likeRespMapper = likeRespMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LikeResponse>> findAll() {
        List<LikeResponse> likeResponseList = likeService.findAll();
        return ResponseEntity.ok(likeResponseList);
    }

    @GetMapping("/{likeId}")
    public ResponseEntity<LikeResponse> findById(@PathVariable Long likeId) {
        LikeResponse likeResponse = likeService.findById(likeId);
        return ResponseEntity.ok(likeResponse);
    }

    @GetMapping("/post")
    public ResponseEntity<List<LikeResponse>> findByPostId(
            @RequestParam Long postId) {
        List<LikeResponse> likeResponseList = likeService.findAllByPostId(postId);
        return ResponseEntity.ok(likeResponseList);
    }

    @GetMapping("/user")
    public ResponseEntity<List<LikeResponse>> findByUserId(
            @RequestParam Long userId) {
        List<LikeResponse> likeResponseList = likeService.findAllByUserId(userId);
        return ResponseEntity.ok(likeResponseList);
    }

    @GetMapping
    public ResponseEntity<List<LikeResponse>> findByUserIdAndPostId(
            @RequestParam Long userId, @RequestParam Long postId) {
        List<LikeResponse> likeResponseList = likeService.findAllByUserId(userId);
        return ResponseEntity.ok(likeResponseList);
    }

    @PostMapping
    public ResponseEntity<LikeResponse> createLike(@RequestBody LikeCreateRequest request) {
        LikeResponse likeResponse = likeService.createLike(request);
        return ResponseEntity.ok(likeResponse);
    }

    @PutMapping("/{likeId}")
    public ResponseEntity<LikeResponse> updateLike(@PathVariable Long likeId,
                                                   @RequestBody LikeUpdateRequest request) {
        LikeResponse likeResponse = likeService.findById(likeId);
        LikeResponse savedLike = new LikeResponse();
        if (likeResponse != null) {
            savedLike = likeService.updateLike(likeId, request);
        }
        return ResponseEntity.ok(savedLike);
    }

    @DeleteMapping("/{likeId}")
    public ResponseEntity<String> deleteById(@PathVariable Long likeId) {
        likeService.deleteLike(likeId);
        return new ResponseEntity<>(likeId + " like has been deleted",
                HttpStatus.OK);
    }
}
