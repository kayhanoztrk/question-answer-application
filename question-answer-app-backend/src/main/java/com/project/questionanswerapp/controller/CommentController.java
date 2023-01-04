package com.project.questionanswerapp.controller;

import com.project.questionanswerapp.model.response.CommentResponse;
import com.project.questionanswerapp.model.request.CommentCreateRequest;
import com.project.questionanswerapp.model.request.CommentUpdateRequest;
import com.project.questionanswerapp.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/v0/comments")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CommentResponse>> findAll() {
        List<CommentResponse> commentResponseList = commentService.findAll();
        return ResponseEntity.ok(commentResponseList);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> findAllCommentsByPostIdOrUserId(
            @RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId) {

        List<CommentResponse> commentResponseList = commentService.findAllCommentsByPostIdOrUserId(userId, postId);
        return ResponseEntity.ok(commentResponseList);
    }

    @GetMapping("/{commentId}")
    public CommentResponse findById(@PathVariable Long commentId) {
        return commentService.findById(commentId);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CommentCreateRequest request) {
        CommentResponse commentResponse = commentService.createComment(request);
        return ResponseEntity.ok(commentResponse);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId,
                                                         @Valid @RequestBody CommentUpdateRequest request) {
        CommentResponse commentResponse = commentService.updateComment(commentId, request);
        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>("Comment" + commentId + " deleted!",
                HttpStatus.OK);
    }
}
