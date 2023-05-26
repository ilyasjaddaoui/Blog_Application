package com.example.Spring_Boot_blog_App.services;

import com.example.Spring_Boot_blog_App.dtos.LoginDto;
import com.example.Spring_Boot_blog_App.dtos.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
