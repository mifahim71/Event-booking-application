package com.eventbooking.auth_service.service;

import com.eventbooking.auth_service.dtos.UserLoginRequestDto;
import com.eventbooking.auth_service.dtos.UserRegisterRequestDto;
import com.eventbooking.auth_service.dtos.UserRegisterResponseDto;
import com.eventbooking.auth_service.entities.User;
import com.eventbooking.auth_service.exceptions.InvalidRoleException;
import com.eventbooking.auth_service.mapper.UserMapper;
import com.eventbooking.auth_service.repository.UserRepository;
import com.eventbooking.auth_service.utils.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final JwtUtil jwtUtil;


    //Register a new user
    public UserRegisterResponseDto registerUser(@Valid UserRegisterRequestDto requestDto) {

        //check if role isn't equal to admin
        if(Objects.equals(requestDto.getRoles().toString(), "ADMIN")){
            throw new InvalidRoleException("users and organizers can't be admins");
        }

        //create a new user
        User user = User.builder()
                .email(requestDto.getEmail())
                .userName(requestDto.getUserName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .roles(requestDto.getRoles())
                .build();

        //save user to the database
        User savedUser = userRepository.save(user);

        //return created user response
        return userMapper.registerResponseDto(savedUser);
    }


    //Authenticate if user exist with email and password
    public Optional<String> authenticate(UserLoginRequestDto loginRequestDto){

        return userRepository.findByEmail(loginRequestDto.getEmail())
                .filter(user -> passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
                .map(user -> jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRoles().toString()));
    }


    //validate if Token is valid
    public boolean validateToken(String token){
        try{
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e){
            return false;
        }
    }
}
