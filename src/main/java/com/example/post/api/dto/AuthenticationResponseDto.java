package com.example.post.api.dto;

public class AuthenticationResponseDto {

    private String token;
    private String refreshToken;

    public AuthenticationResponseDto(){}

    public AuthenticationResponseDto(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
