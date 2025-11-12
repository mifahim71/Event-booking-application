package com.eventbooking.auth_service.controller;

import com.eventbooking.auth_service.dtos.*;
import com.eventbooking.auth_service.exceptions.InvalidEmailAndPasswordException;
import com.eventbooking.auth_service.service.AuthService;
import com.eventbooking.auth_service.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    //register user controller
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> registerUser(
            @Valid @RequestBody UserRegisterRequestDto requestDto
    ){

        //call auth service
        UserRegisterResponseDto responseDto = authService.registerUser(requestDto);

        //finally return the value
        return ResponseEntity.ok().body(responseDto);
    }


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDto> loginUser(
            @RequestBody UserLoginRequestDto requestDto
    ){
        //validate if user exist in auth service
        Optional<String> token = authService.authenticate(requestDto);

        //is returns empty then return Un authorized
        if(token.isEmpty()){
            throw new InvalidEmailAndPasswordException("Invalid email or password");
        }

        UserLoginResponseDto responseDto = new UserLoginResponseDto();
        responseDto.setJwtToken(token.get());
        responseDto.setExpiryDate(LocalDateTime.now().plusHours(1));
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/validate")
    public ResponseEntity<UserDetailsDto> validateToken(@RequestHeader("Authorization") String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);

        if(!authService.validateToken(authHeader.substring(7))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUserId(jwtUtil.getUserId(token));
        userDetailsDto.setEmail(jwtUtil.getEmail(token));
        userDetailsDto.setRoles(jwtUtil.getRoles(token));


        return ResponseEntity.ok().body(userDetailsDto);
    }

    @GetMapping("/health-check")
    public ResponseEntity<String> heathCheck(){
        return ResponseEntity.ok().body("health-check");
    }
}
