package com.Blog.Controller;

import com.Blog.PayLoad.CommentDto;
import com.Blog.Service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    //http://localhost:8080/api/posts/{postId}/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> newComment(@RequestBody CommentDto commentDto, @PathVariable("postId") Long postId){
        CommentDto returnCommentDto = commentService.createComment(postId,commentDto);
        return new ResponseEntity<>(returnCommentDto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/post/{postId}/comments/{id}
    @GetMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> retriveComment(@PathVariable("postId") Long postId , @PathVariable("commentId") Long commentId){
        CommentDto readCommentDto = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(readCommentDto, HttpStatus.OK);
    }

    // http://localhost:8080/api/post/{postId}/comments/{commentId}
    @DeleteMapping("/post/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment delete successfully", HttpStatus.OK);
    }

    // http://localhost:8080/api/post/{postId}/comments/{commentId}
    @PutMapping("post/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId){
        CommentDto updatedComment = commentService.updateComment(postId,commentId,commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.CREATED);
    }
}

