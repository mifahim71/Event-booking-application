package com.eventbooking.event_service.exception;

public class InvalidAccessForUserException extends RuntimeException {
    public InvalidAccessForUserException(String message) {
        super(message);
    }
}
