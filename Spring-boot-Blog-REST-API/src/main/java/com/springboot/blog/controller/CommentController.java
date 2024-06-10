package com.springboot.blog.controller;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "id") long id,
                                                    @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(id,commentDto), HttpStatus.CREATED);

    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentByPostId(@PathVariable(value = "postId") long postId){
        return commentService.getAllPosts(postId);
    }
    @GetMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") long postId,@PathVariable(value = "commentId") long commentId){
    CommentDto commentDto=commentService.getCommentById(postId,commentId);
    return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") long postId,@PathVariable(value = "commentId") long commentId,@RequestBody CommentDto commentDto){
    CommentDto commentDto1=commentService.updateCommentById(postId,commentId,commentDto);
    return new ResponseEntity<>(commentDto1,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") long postId,@PathVariable(value = "commentId") long commentId){
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("Comment deleted",HttpStatus.OK);
    }
}
