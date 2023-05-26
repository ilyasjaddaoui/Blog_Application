package com.example.Spring_Boot_blog_App.dtos;

import lombok.*;

@Data
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthResponse() {
        this.accessToken = accessToken;
    }
}
