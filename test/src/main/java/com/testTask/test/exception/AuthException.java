package com.testTask.test.exception;

public class AuthException extends RuntimeException{
    public AuthException(String message) {
        super("Registration error: " + message);
    }
}

