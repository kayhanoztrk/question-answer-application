package com.project.questionanswerapp.exception;

/**
 * @author Kayhan Öztürk
 * @version 0.1
 * @since 0.1
 */
public class PostNotFoundException extends ApiNotFoundException {
    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
