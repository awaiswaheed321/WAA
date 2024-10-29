package edu.mid.lab1.controllers;

import edu.mid.lab1.entities.dtos.PostDto;
import edu.mid.lab1.entities.dtos.PostRequestDto;
import edu.mid.lab1.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable long id) {
        PostDto p = postService.findPostById(id);
        if (p == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(@RequestParam(value = "author", required = false) String author,
                                                                      @RequestParam(value = "author-containing", required = false) String authorContaining) {
        List<PostDto> dtos = postService.getFilteredPostsByAuthorName(author, authorContaining);
        if (dtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(dtos);
        }
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        PostDto createdPost = postService.savePost(postRequestDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long id, @RequestBody PostRequestDto postRequestDto) {
        PostDto p = postService.updatePost(id, postRequestDto);
        if (p == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostDto> deletePost(@PathVariable long id) {
        postService.deletePostById(id);
        return ResponseEntity.ok().build();
    }
}
