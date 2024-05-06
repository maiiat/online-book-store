package com.example.exception;

public class CartItemAbsenceException extends RuntimeException {
    public CartItemAbsenceException(String message) {
        super(message);
    }
}
