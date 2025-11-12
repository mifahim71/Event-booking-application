package com.eventbooking.event_service.exception;

import com.eventbooking.event_service.dtos.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        List<ErrorMessageDto> errors = ex.getBindingResult().getFieldErrors().stream().map(error -> new ErrorMessageDto(error.getDefaultMessage())).toList();
        return ResponseEntity.badRequest().body(errors);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessageDto> handleInvalidAccessForUser(IllegalArgumentException ex){
        return ResponseEntity.badRequest().body(new ErrorMessageDto(ex.getMessage()));
    }

    @ExceptionHandler(InvalidAccessForUserException.class)
    public ResponseEntity<ErrorMessageDto> handleInvalidAccessForUser(InvalidAccessForUserException ex){
        return ResponseEntity.badRequest().body(new ErrorMessageDto(ex.getMessage()));
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorMessageDto> handleEventNotFound(EventNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessageDto(ex.getMessage()));
    }
}
