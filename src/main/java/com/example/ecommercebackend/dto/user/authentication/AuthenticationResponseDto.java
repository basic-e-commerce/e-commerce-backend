package com.example.ecommercebackend.dto.user.authentication;

public class AuthenticationResponseDto {
    private String accessToken;
    private String firstName;
    private String lastName;
    private String username;
    private String role;

    public AuthenticationResponseDto(String accessToken, String firstName, String lastName, String username, String role) {
        this.accessToken = accessToken;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
