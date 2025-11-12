package com.eventbooking.auth_service.mapper;

import com.eventbooking.auth_service.dtos.UserRegisterResponseDto;
import com.eventbooking.auth_service.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserRegisterResponseDto registerResponseDto(User user){

        UserRegisterResponseDto responseDto = new UserRegisterResponseDto();
        responseDto.setId(user.getId());
        responseDto.setRoles(user.getRoles());
        responseDto.setEmail(user.getEmail());
        responseDto.setUserName(user.getUserName());

        return responseDto;
    }
}
