package edu.miu.labs.controllers;

import edu.miu.labs.entities.dtos.CommentDto;
import edu.miu.labs.entities.dtos.CommentRequestDto;
import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.PostRequestDto;
import edu.miu.labs.service.LoggerService;
import edu.miu.labs.service.PostService;
import org.springframework.http.HttpStatus;
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

    /**
     * Creates a new comment for a specific post by ID.
     *
     * @param id                The ID of the user for whom to create the post.
     * @param commentRequestDto The data for the post to create.
     * @return A ResponseEntity with HTTP status 201 if the post is successfully created.
     */
    @PostMapping("/{id}/comment")
    public ResponseEntity<Void> createComment(@PathVariable long id, @RequestBody CommentRequestDto commentRequestDto) {
        logger.info(PostController.class.getName(), String.format("POST /api/v1/post/%d/comment called with request body: %s", id, commentRequestDto));
        postService.saveComment(id, commentRequestDto);
        logger.info(PostController.class.getName(), String.format("POST /api/v1/post/%d/comment - Comment created successfully.", id));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Retrieves posts that match a given title.
     *
     * @param title The title to match with the posts to retrieve.
     * @return A ResponseEntity containing a list of PostDto if found, or a no content status if not found.
     */
    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<PostDto>> getPostMatchingTitle(@PathVariable String title) {
        logger.info(PostController.class.getName(), "GET /api/v1/post/" + title + " called with path variable title: " + title);
        List<PostDto> posts = postService.getPostsMatchingTitle(title);
        if (posts.isEmpty()) {
            logger.info(PostController.class.getName(), "GET /api/v1/post/" + title + " - No posts found with title containing: " + title);
            return ResponseEntity.noContent().build();
        } else {
            logger.info(PostController.class.getName(), "GET /api/v1/post/" + title + " - Posts found: " + posts.size());
            return ResponseEntity.ok(posts);
        }
    }

    /**
     * Retrieves comments associated with a specific post by ID.
     *
     * @param id The ID of the post whose comments are to be retrieved.
     * @return A ResponseEntity containing a list of CommentDto if comments are found, or no content if empty.
     */
    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> getComments(@PathVariable long id) {
        logger.info(UserController.class.getName(), "GET /api/v1/post/" + id + "/comments called with path variable id: " + id);
        List<CommentDto> comments = postService.getCommentsByPostId(id);
        if (comments.isEmpty()) {
            logger.info(UserController.class.getName(), "GET /api/v1/post/" + id + "/comments - No comments found for post with ID: " + id);
            return ResponseEntity.noContent().build();
        } else {
            logger.info(UserController.class.getName(), "GET /api/v1/post/" + id + "/comments - Comments found: Count = " + comments.size());
            return ResponseEntity.ok(comments);
        }
    }


}
