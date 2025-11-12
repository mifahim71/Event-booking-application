package com.eventbooking.api_gateway.dto;

public class UserDetailsDto {

    public UserDetailsDto() { }

    public UserDetailsDto(String userId, String email, String roles) {
        this.userId = userId;
        this.email = email;
        this.roles = roles;
    }

    private String userId;

    private String email;

    private String roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
