package com.eventbooking.auth_service.exceptions;

public class InvalidEmailAndPasswordException extends RuntimeException {
    public InvalidEmailAndPasswordException(String message) {
        super(message);
    }
}
