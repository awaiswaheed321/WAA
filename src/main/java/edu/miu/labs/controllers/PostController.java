package edu.miu.labs.controllers;

import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.PostRequestDto;
import edu.miu.labs.service.LoggerService;
import edu.miu.labs.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/post")
public class PostController {
    private final PostService postService;
    private final LoggerService logger;

    public PostController(PostService postService, LoggerService logger) {
        this.postService = postService;
        this.logger = logger;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable long id) {
        logger.info(PostController.class.getName(), "GET /api/v1/post/" + id + " called with path variable id: " + id);
        PostDto post = postService.findPostById(id);
        logger.info(PostController.class.getName(), "GET /api/v1/post/" + id + " - Post found: " + post);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "author-containing", required = false) String authorContaining) {
        logger.info(PostController.class.getName(), "GET /api/v1/post called with request params - author: " + author + ", author-containing: " + authorContaining);
        List<PostDto> posts = postService.getFilteredPostsByAuthorName(author, authorContaining);
        if (posts.isEmpty()) {
            logger.info(PostController.class.getName(), "GET /api/v1/post - No posts found for the given filters.");
            return ResponseEntity.noContent().build();
        } else {
            logger.info(PostController.class.getName(), "GET /api/v1/post - Posts found: " + posts);
            return ResponseEntity.ok(posts);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long id, @RequestBody PostRequestDto postRequestDto) {
        logger.info(PostController.class.getName(), "PUT /api/v1/post/" + id + " called with path variable id: " + id + " and request body: " + postRequestDto);
        PostDto updatedPost = postService.updatePost(id, postRequestDto);
        logger.info(PostController.class.getName(), "PUT /api/v1/post/" + id + " - Post updated: " + updatedPost);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        logger.info(PostController.class.getName(), "DELETE /api/v1/post/" + id + " called with path variable id: " + id);
        postService.deletePostById(id);
        logger.info(PostController.class.getName(), "DELETE /api/v1/post/" + id + " - Post deleted.");
        return ResponseEntity.ok().build();
    }
}
