package com.eventbooking.auth_service.exceptions;

import com.eventbooking.auth_service.dtos.ErrorMessageDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDto>> handleValidationExceptions(MethodArgumentNotValidException ex){

        List<ErrorMessageDto> errors = ex.getBindingResult().getFieldErrors().stream().map(error ->
                new ErrorMessageDto(error.getDefaultMessage())
        ).toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessageDto> handleDataIntegrationViolation(DataIntegrityViolationException ex){

        ErrorMessageDto responseDto = new ErrorMessageDto();
        responseDto.setMessage("Duplicate value or constraint violation");

        if(ex.getMessage().contains("email")){
            responseDto.setMessage("Email address already exists");
        } else if (ex.getMessage().contains("userName")){
            responseDto.setMessage("User name already exists");
        }

        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessageDto> handleMessageNotReadable(HttpMessageNotReadableException ex){

        String message = "Invalid request format or enum value";

        // Optional: customize message if it's an enum issue
        if (ex.getMessage().contains("Roles")) {
            message = "Invalid value for roles. Allowed values: USER, ORGANIZER, ADMIN";
        }

        return ResponseEntity.badRequest().body(new ErrorMessageDto(message));
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<ErrorMessageDto> handleInvalidRoleExceptions(InvalidRoleException ex){

        return ResponseEntity.badRequest().body(new ErrorMessageDto(ex.getMessage()));
    }

    @ExceptionHandler(InvalidEmailAndPasswordException.class)
    public ResponseEntity<ErrorMessageDto> handleInvalidEmailAndPassword(InvalidEmailAndPasswordException ex){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessageDto(ex.getMessage()));
    }
}
