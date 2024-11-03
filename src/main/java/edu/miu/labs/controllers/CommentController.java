package edu.miu.labs.controllers;

import edu.miu.labs.entities.dtos.CommentDto;
import edu.miu.labs.entities.dtos.CommentRequestDto;
import edu.miu.labs.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing comments.
 * Provides endpoints for retrieving, updating, and deleting comments.
 */
@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Retrieves a specific comment by its ID.
     *
     * @param id The ID of the comment to retrieve.
     * @return A ResponseEntity containing the CommentDto if found, or an error status if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable long id) {
        CommentDto comment = commentService.findCommentById(id);
        return ResponseEntity.ok(comment);
    }

    /**
     * Retrieves a list of comments, optionally filtered by author or a substring of the author's name.
     *
     * @return A ResponseEntity containing a list of CommentDto objects, or no content if no comments are found.
     */
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> comments = commentService.getAllComments();
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(comments);
        }
    }

    /**
     * Updates an existing comment by its ID.
     *
     * @param id                The ID of the comment to update.
     * @param commentRequestDto The data to update the comment with.
     * @return A ResponseEntity containing the updated CommentDto if successful.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updatePost(@PathVariable long id, @RequestBody CommentRequestDto commentRequestDto) {
        CommentDto updatedPost = commentService.updateComment(id, commentRequestDto);
        return ResponseEntity.ok(updatedPost);
    }

    /**
     * Deletes a specific comment by its ID.
     *
     * @param id The ID of the comment to delete.
     * @return A ResponseEntity with a success status if deletion is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        commentService.deleteCommentById(id);
        return ResponseEntity.ok().build();
    }
}
