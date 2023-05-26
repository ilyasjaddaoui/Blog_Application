package com.example.Spring_Boot_blog_App.services.Imp;

import com.example.Spring_Boot_blog_App.dtos.CommentDto;
import com.example.Spring_Boot_blog_App.entities.Comment;
import com.example.Spring_Boot_blog_App.entities.Post;
import com.example.Spring_Boot_blog_App.exceptions.BlogApiException;
import com.example.Spring_Boot_blog_App.exceptions.ResourceNotFoundException;
import com.example.Spring_Boot_blog_App.repositories.CommentRepository;
import com.example.Spring_Boot_blog_App.repositories.PostRepository;
import com.example.Spring_Boot_blog_App.services.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    private PostRepository postRepository;
    private ModelMapper mapper;


    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        Comment comment =mapToEntity(commentDto);

        // get post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", postId));

        // set post to comment
        comment.setPost(post);

        // save comment entity to DB
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        // get comments by postId
        List<Comment> comments=commentRepository.findByPostId(postId);

        // convert list of comments entity to list of comment DTO
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // get post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", postId));

        Comment comment= commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) {
        // get post by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", postId));

        // get comment by id
        Comment comment= commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);


    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // get post by id
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", "id", postId));

        // get comment by id
        Comment comment= commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        commentRepository.delete(comment);
    }


     /*
     ===========================================================
        *  Methods for doing Mappers between Entity and DTO
     ===========================================================
    */

    private CommentDto mapToDto(Comment comment){

        CommentDto commentDto= mapper.map(comment, CommentDto.class);

    /*
    =========================================================
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
    ==========================================================
    */


        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){

        Comment comment = mapper.map(commentDto, Comment.class);
    /*
    ==========================================================
        Comment comment=new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
    ==========================================================
     */

        return comment;
    }
}
