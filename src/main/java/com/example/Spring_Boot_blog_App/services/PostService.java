package com.example.Spring_Boot_blog_App.services;

import com.example.Spring_Boot_blog_App.dtos.PostDto;
import com.example.Spring_Boot_blog_App.dtos.PostResponse;
import com.example.Spring_Boot_blog_App.entities.Post;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePost(Long id);
    List<PostDto> getPostByCategory(Long categoryId);
}
