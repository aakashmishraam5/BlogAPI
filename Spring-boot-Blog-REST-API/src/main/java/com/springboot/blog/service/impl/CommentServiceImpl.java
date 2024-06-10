package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private ModelMapper mapper;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository,PostRepository postRepository,ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository=postRepository;
        this.mapper=mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment=mapToEntity(commentDto);
        Post post=postRepository.findById(postId).orElseThrow(() ->
                 new ResourceNotFoundException("Post","id",postId));
        comment.setPost(post);
        Comment newcomment=commentRepository.save(comment);
       return mapToDto(newcomment);


    }

    @Override
    public List<CommentDto> getAllPosts(long postId) {
        List<Comment> comments=commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","id",postId)) ;
        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment" ,"commentId",commentId));
        //comment not belongs to a post then throw this exception
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"no mapping for blog id and comment it");
        }


        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId,CommentDto commentRequest) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","Id",postId));

        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment","id",commentId));

                if(!comment.getPost().getId().equals(post.getId())){
                    throw new BlogAPIException(HttpStatus.BAD_REQUEST,"NO comment to update");}

                comment.setName(commentRequest.getName());
                comment.setEmail(commentRequest.getEmail());
                comment.setBody(commentRequest.getBody());

               Comment updatedComment= commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        Post post=postRepository.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("Post","Id",postId));

        Comment comment=commentRepository.findById(commentId).orElseThrow(
                ()->new ResourceNotFoundException("Comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"NO comment to update");}

         commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto=mapper.map(comment,CommentDto.class);
//        CommentDto commentDto=new CommentDto();
//        commentDto.setBody(comment.getBody());
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment=mapper.map(commentDto,Comment.class);
//        Comment comment=new Comment();
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
        return comment;
    }
}
