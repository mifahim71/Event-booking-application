package com.eventbooking.booking_service.exceptions;

import com.eventbooking.booking_service.dtos.ErrorMessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){

        List<ErrorMessageDto> errorMessages = ex.getBindingResult().getFieldErrors().stream().map(error -> {
            return new ErrorMessageDto(error.getDefaultMessage());
        }).toList();

        return ResponseEntity.badRequest().body(errorMessages);
    }

    @ExceptionHandler(SeatLimitExceededException.class)
    public ResponseEntity<ErrorMessageDto> handleSeatLimitExceededException(SeatLimitExceededException ex){
        return ResponseEntity.badRequest().body(new ErrorMessageDto(ex.getMessage()));
    }
}
