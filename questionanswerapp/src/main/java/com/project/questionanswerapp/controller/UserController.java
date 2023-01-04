package com.project.questionanswerapp.controller;

import com.project.questionanswerapp.entity.User;
import com.project.questionanswerapp.model.response.UserResponse;
import com.project.questionanswerapp.service.UserService;
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
@RequestMapping("/api/v0/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUser() {
        List<UserResponse> userList = userService.findAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long userId) {
        UserResponse userResponse = userService.findUserById(userId);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody User newUser) {
        UserResponse userResponse = userService.createUser(newUser);
        logger.info("user info:" + newUser.getUsername());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable Long userId,
                                                       @RequestBody User newUser) {
        UserResponse userResponse = userService.updateUserById(userId, newUser);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return new ResponseEntity<>("DELETED", HttpStatus.OK);
    }


    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivity(@PathVariable Long userId){
        return userService.getUserActivity(userId);
    }


}
