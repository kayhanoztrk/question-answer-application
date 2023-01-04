package com.project.questionanswerapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public class LikeNotFoundException extends ApiNotFoundException {

    public LikeNotFoundException(String message) {
        super(message);
    }

    public LikeNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
