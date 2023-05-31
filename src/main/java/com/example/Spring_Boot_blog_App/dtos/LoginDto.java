package com.example.Spring_Boot_blog_App.dtos;


import lombok.*;

@Data

public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
