package com.project.questionanswerapp.controller;

import com.project.questionanswerapp.model.response.PostResponse;
import com.project.questionanswerapp.model.request.PostCreateRequest;
import com.project.questionanswerapp.model.request.PostUpdateRequest;
import com.project.questionanswerapp.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@RestController
@RequestMapping("/api/v0/post")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @GetMapping("/getAll")
    public ResponseEntity<List<PostResponse>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAllPostByUserId(@RequestParam
                                                                     Optional<Long> userId) {
        return new ResponseEntity<>(postService.findAllPostByUserId(userId),
                HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findById(postId));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateRequest newPost) {
        PostResponse postResponse = postService.createPost(newPost);
        return ResponseEntity.ok(postResponse);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePostById(@PathVariable Long postId,
                                                       @RequestBody PostUpdateRequest postUpdateRequest) {
        PostResponse postResponse = postService.updatePostById(postId, postUpdateRequest);
        return ResponseEntity.ok(postResponse);
    }
}
