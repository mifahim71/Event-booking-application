package com.eventbooking.auth_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDto {

    private String jwtToken;

    private LocalDateTime expiryDate;

}
