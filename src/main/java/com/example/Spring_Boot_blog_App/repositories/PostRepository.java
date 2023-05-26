package com.example.Spring_Boot_blog_App.repositories;

import com.example.Spring_Boot_blog_App.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByCategoryId(Long categoryId);
}
