package com.geekbrains.homework6.api;


// для отправки ответа в виде токена от back-end на front

public class JwtResponse {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public JwtResponse() {
    }

    public JwtResponse(String token) {
        this.token = token;
    }
}
