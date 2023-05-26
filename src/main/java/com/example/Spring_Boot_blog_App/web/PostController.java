package com.example.Spring_Boot_blog_App.web;

import com.example.Spring_Boot_blog_App.dtos.PostDto;
import com.example.Spring_Boot_blog_App.dtos.PostResponse;
import com.example.Spring_Boot_blog_App.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD REST APIs for Post Resource")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // Create Post
    @Operation(summary = "Create Post REST API", description = "Create Post REST API to save posts in DB ") //SWAGGER
    @SecurityRequirement(name = "Bear Authentication") // SWAGGER
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED") //SWAGGER
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    // Get all posts
    @GetMapping
    public PostResponse getAllPosts( /* Pagination & Sorting */
                                    @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                    @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }
    // Get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id){
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK);
    }
    // update post
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("id") Long id){
        PostDto responsePostDto= postService.updatePost(postDto, id);
        return new ResponseEntity<>(responsePostDto, HttpStatus.OK);
    }
    // delete post
    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long id){
        postService.deletePost(id);
        return new ResponseEntity<>("this post deleted successfully",HttpStatus.OK);
    }

    // build Get post by Category REST API
    @GetMapping("/category/{categoryId}")
    private ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("categoryId") Long categoryId){
        List<PostDto> postDtos = postService.getPostByCategory(categoryId);

        return ResponseEntity.ok(postDtos);
    }

}
