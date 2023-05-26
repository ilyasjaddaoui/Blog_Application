package com.example.Spring_Boot_blog_App.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    @NotEmpty(message = "Name should not be EMPTY")
    @Size(min = 3, message = "Name should have at least 3 characters")
    private String name;

    @NotEmpty(message = "Email should not be EMPTY")
    @Email
    private String email;

    @NotEmpty(message = "Body should not be EMPTY")
    @Size(min = 10, message = "Body should have at least 10 characters")
    private String body;
}
