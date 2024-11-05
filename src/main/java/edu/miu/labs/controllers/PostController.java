package edu.miu.labs.controllers;

import edu.miu.labs.entities.dtos.CommentDto;
import edu.miu.labs.entities.dtos.CommentRequestDto;
import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.PostRequestDto;
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

    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Retrieves a specific post by its ID.
     *
     * @param id The ID of the post to retrieve.
     * @return A ResponseEntity containing the PostDto if found, or an error status if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable long id) {
        PostDto post = postService.findPostById(id);
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
        List<PostDto> posts = postService.getFilteredPostsByAuthorName(author, authorContaining);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
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
        PostDto updatedPost = postService.updatePost(id, postRequestDto);
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
        postService.deletePostById(id);
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
        postService.saveComment(id, commentRequestDto);
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
        List<PostDto> posts = postService.getPostsMatchingTitle(title);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
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
        List<CommentDto> comments = postService.getCommentsByPostId(id);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(comments);
        }
    }
}
