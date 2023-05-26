package com.example.Spring_Boot_blog_App.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RegisterDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
