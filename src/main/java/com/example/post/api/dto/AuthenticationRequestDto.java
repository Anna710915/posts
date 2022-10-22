package com.example.post.api.dto;

public class AuthenticationRequestDto {

    private String username;
    private String password;

    public AuthenticationRequestDto(){}

    public AuthenticationRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
