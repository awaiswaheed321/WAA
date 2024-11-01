package edu.miu.labs.controllers;

import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.PostRequestDto;
import edu.miu.labs.service.LoggerService;
import edu.miu.labs.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing posts.
 * Provides endpoints for retrieving, updating, and deleting posts.
 */
@RestController
@RequestMapping("api/v1/post")
public class PostController {
    private final PostService postService;
    private final LoggerService logger;

    public PostController(PostService postService, LoggerService logger) {
        this.postService = postService;
        this.logger = logger;
    }

    /**
     * Retrieves a specific post by its ID.
     *
     * @param id The ID of the post to retrieve.
     * @return A ResponseEntity containing the PostDto if found, or an error status if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable long id) {
        logger.info(PostController.class.getName(), "GET /api/v1/post/" + id + " called with path variable id: " + id);
        PostDto post = postService.findPostById(id);
        logger.info(PostController.class.getName(), "GET /api/v1/post/" + id + " - Post found: " + post);
        return ResponseEntity.ok(post);
    }

    /**
     * Retrieves a list of posts, optionally filtered by author or a substring of the author's name.
     *
     * @param author           The exact author name to filter posts by (optional).
     * @param authorContaining A substring of the author's name to filter posts by (optional).
     * @return A ResponseEntity containing a list of PostDto objects, or no content if no posts are found.
     */
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

    /**
     * Updates an existing post by its ID.
     *
     * @param id             The ID of the post to update.
     * @param postRequestDto The data to update the post with.
     * @return A ResponseEntity containing the updated PostDto if successful.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long id, @RequestBody PostRequestDto postRequestDto) {
        logger.info(PostController.class.getName(), "PUT /api/v1/post/" + id + " called with path variable id: " + id + " and request body: " + postRequestDto);
        PostDto updatedPost = postService.updatePost(id, postRequestDto);
        logger.info(PostController.class.getName(), "PUT /api/v1/post/" + id + " - Post updated: " + updatedPost);
        return ResponseEntity.ok(updatedPost);
    }

    /**
     * Deletes a specific post by its ID.
     *
     * @param id The ID of the post to delete.
     * @return A ResponseEntity with a success status if deletion is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        logger.info(PostController.class.getName(), "DELETE /api/v1/post/" + id + " called with path variable id: " + id);
        postService.deletePostById(id);
        logger.info(PostController.class.getName(), "DELETE /api/v1/post/" + id + " - Post deleted.");
        return ResponseEntity.ok().build();
    }
}
