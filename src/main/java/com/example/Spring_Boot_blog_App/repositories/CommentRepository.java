package com.example.Spring_Boot_blog_App.repositories;

import com.example.Spring_Boot_blog_App.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long id);
}
