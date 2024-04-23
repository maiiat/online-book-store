package com.example.exception;

public class IsbnAlreadyExistException extends RuntimeException {
    public IsbnAlreadyExistException(String message) {
        super(message);
    }
}
