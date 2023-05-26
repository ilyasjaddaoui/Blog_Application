package com.example.Spring_Boot_blog_App.dtos;

import lombok.*;

@Data
public class RegisterDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
