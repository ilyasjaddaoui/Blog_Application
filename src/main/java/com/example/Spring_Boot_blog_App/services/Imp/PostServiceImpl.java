package com.example.Spring_Boot_blog_App.services.Imp;

import com.example.Spring_Boot_blog_App.dtos.PostDto;
import com.example.Spring_Boot_blog_App.dtos.PostResponse;
import com.example.Spring_Boot_blog_App.entities.Category;
import com.example.Spring_Boot_blog_App.entities.Post;
import com.example.Spring_Boot_blog_App.exceptions.ResourceNotFoundException;
import com.example.Spring_Boot_blog_App.repositories.CategoryRepository;
import com.example.Spring_Boot_blog_App.repositories.PostRepository;
import com.example.Spring_Boot_blog_App.services.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    private ModelMapper mapper; // faire la convertion enter les entity et les dto's automatiquement
    private CategoryRepository categoryRepository;
    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        // convert DTO to Entity with the methode mapToEntity
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        // convert Entity to DTO with the methode mapToDTO
        PostDto postResponse = mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable= PageRequest.of(pageNo,pageSize, sort );

        Page<Post> posts = postRepository.findAll(pageable);

        //get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        // get post by id from the dataBase
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post updatedPost = postRepository.save(post);
        // converting to DTO
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        // Get post by id from the dataBase
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post", "id", id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map((post)-> mapToDTO(post)).collect(Collectors.toList());
    }




    /*
     ===========================================================
        *  Methods for doing Mappers between Entity and DTO
     ===========================================================
    */

    // methode for converting Entity to DTO
    private PostDto mapToDTO(Post post){

        // Automatic methode
        PostDto postDto = mapper.map(post, PostDto.class);

    /*

        ** From this manuel methods
    ======================================================
        PostDto postDto=new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
    ======================================================
    */

        return postDto;
    }
    private Post mapToEntity(PostDto postDto){
        // Automatic methode

        Post post = mapper.map(postDto, Post.class);

    /*
         ** From this manuel methods
    =====================================================
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
    ======================================================
     */
        return post;
    }


}
