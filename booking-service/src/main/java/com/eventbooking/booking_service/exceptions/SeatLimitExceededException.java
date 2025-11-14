package com.eventbooking.booking_service.exceptions;

public class SeatLimitExceededException extends RuntimeException {
    public SeatLimitExceededException(String message) {
        super(message);
    }
}
