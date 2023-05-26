package com.example.Spring_Boot_blog_App.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
}
