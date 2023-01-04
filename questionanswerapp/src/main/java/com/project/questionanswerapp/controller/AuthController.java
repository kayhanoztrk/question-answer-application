package com.project.questionanswerapp.controller;

import com.project.questionanswerapp.entity.RefreshToken;
import com.project.questionanswerapp.entity.User;
import com.project.questionanswerapp.model.request.RefreshRequest;
import com.project.questionanswerapp.model.request.UserRequest;
import com.project.questionanswerapp.model.response.AuthResponse;
import com.project.questionanswerapp.model.response.UserResponse;
import com.project.questionanswerapp.security.JwtTokenProvider;
import com.project.questionanswerapp.service.RefreshTokenService;
import com.project.questionanswerapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          UserService userService,
                          PasswordEncoder passwordEncoder,
                          RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        UserResponse userResp = userService.findUserByName(request.getUsername());
        User user = new User();
        user.setId(userResp.getId());
        user.setUsername(userResp.getUsername());
        user.setPassword(userResp.getPassword());

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getId());

        logger.info("authMessage" + authResponse.getMessage());

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest request) {
        AuthResponse authResponse = new AuthResponse();
        if (userService.findUserByName(request.getUsername()) != null) {
            authResponse.setMessage("username already in use");
        } else {

            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userService.createUser(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            Authentication auth = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(auth);
            String jwtToken = jwtTokenProvider.generateJwtToken(auth);
            authResponse.setAccessToken("Bearer " + jwtToken);
            authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
            authResponse.setUserId(user.getId());
            authResponse.setMessage("user has been created successfully");
        }
        logger.info("authResponseGetMessage" + authResponse.getMessage());
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody
                                                RefreshRequest request) {
        AuthResponse response = new AuthResponse();
        RefreshToken refreshToken = refreshTokenService.findByUserId(request.getUserId());

        if (refreshToken.getToken().equals(request.getRefreshToken()) &&
                !refreshTokenService.isRefreshExpired(refreshToken)) {
            User user = refreshToken.getUser();
            String jwtToken = jwtTokenProvider.generateJwtTokenById(user.getId());

            response.setAccessToken("Bearer " + jwtToken);
            response.setRefreshToken(refreshTokenService.createRefreshToken(user));
            response.setUserId(user.getId());
            response.setMessage("user has been created successfully");
        } else {
            response.setMessage("REFRESH TOKEN IS NOT VALID");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
