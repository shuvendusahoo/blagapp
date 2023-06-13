package com.Blog.Controller;

import com.Blog.PayLoad.PostDto;
import com.Blog.PayLoad.PostResponse;
import com.Blog.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //http://localhost:8080/api/posts
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> newPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.OK);
        }
        PostDto returnPostDto = postService.createPost(postDto);
        return new ResponseEntity<>(returnPostDto, HttpStatus.CREATED);
    }

    //    @GetMapping
//    public List<PostDto> readPost(){
//        List<PostDto> readPostDto = postService.readPost();
//        return readPostDto;
//    }
    // I need to develop a PostResponse in payload package because we can not perform pagination
    // in CRUD repository so we need to use JPA repository for pagination.
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Long postId){
        PostDto readById =  postService.getPostById(postId);
        return new ResponseEntity<>(readById, HttpStatus.OK);
    }
    /**This method develop only for the pagination.**/
    //http://localhost:8080/api/posts/?pageNo=0&pageSize=2&sortBy=post_id&sortDir=asc
    @GetMapping
    public PostResponse findAllPost(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable(name = "postId") Long postId){
        postService.deletePostById(postId);
        return new ResponseEntity<>("Id has been deleted", HttpStatus.OK);
    }

    @PutMapping("/{postid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable(name = "postid") Long postid){
        PostDto updateId = postService.updatePost(postDto, postid);
        return new ResponseEntity<>(updateId, HttpStatus.CREATED);
    }
}
