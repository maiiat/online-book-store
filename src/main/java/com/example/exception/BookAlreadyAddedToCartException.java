package com.example.exception;

public class BookAlreadyAddedToCartException extends RuntimeException {
    public BookAlreadyAddedToCartException(String message) {
        super(message);
    }
}
