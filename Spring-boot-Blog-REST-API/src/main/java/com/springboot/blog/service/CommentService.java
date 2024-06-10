package com.springboot.blog.service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId,CommentDto commentDto);
    List<CommentDto> getAllPosts(long postId);
    CommentDto getCommentById(long postId,long commentId);
    CommentDto updateCommentById(long postId,long commentId,CommentDto commentRequest);
    void deleteCommentById(long postId,long commentId);


}
