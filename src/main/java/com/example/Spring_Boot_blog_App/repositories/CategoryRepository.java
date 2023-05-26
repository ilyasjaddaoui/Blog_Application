package com.example.Spring_Boot_blog_App.repositories;

import com.example.Spring_Boot_blog_App.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
