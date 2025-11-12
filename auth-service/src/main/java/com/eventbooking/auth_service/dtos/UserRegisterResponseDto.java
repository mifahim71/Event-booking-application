package com.eventbooking.auth_service.dtos;

import com.eventbooking.auth_service.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponseDto {

    private Long id;

    private String userName;

    private String email;

    private Roles roles;
}
