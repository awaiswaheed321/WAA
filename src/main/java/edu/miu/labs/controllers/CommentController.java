package edu.miu.labs.controllers;

import edu.miu.labs.entities.dtos.CommentDto;
import edu.miu.labs.entities.dtos.CommentRequestDto;
import edu.miu.labs.service.CommentService;
import edu.miu.labs.service.LoggerService;
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
    private final LoggerService logger;

    public CommentController(CommentService commentService, LoggerService logger) {
        this.commentService = commentService;
        this.logger = logger;
    }

    /**
     * Retrieves a specific comment by its ID.
     *
     * @param id The ID of the comment to retrieve.
     * @return A ResponseEntity containing the CommentDto if found, or an error status if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable long id) {
        logger.info(CommentController.class.getName(), "GET /api/v1/comment/" + id + " called with path variable id: " + id);
        CommentDto comment = commentService.findCommentById(id);
        logger.info(CommentController.class.getName(), "GET /api/v1/comment/" + id + " - Comment found: " + comment);
        return ResponseEntity.ok(comment);
    }

    /**
     * Retrieves a list of comments, optionally filtered by author or a substring of the author's name.
     *
     * @return A ResponseEntity containing a list of CommentDto objects, or no content if no comments are found.
     */
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAllComments() {
        logger.info(CommentController.class.getName(), "GET /api/v1/comment called.");
        List<CommentDto> comments = commentService.getAllComments();
        if (comments.isEmpty()) {
            logger.info(CommentController.class.getName(), "GET /api/v1/comment - No comments found.");
            return ResponseEntity.noContent().build();
        } else {
            logger.info(CommentController.class.getName(), "GET /api/v1/comment - Comments found: " + comments);
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
        logger.info(CommentController.class.getName(), "PUT /api/v1/comment/" + id + " called with path variable id: " + id + " and request body: " + commentRequestDto);
        CommentDto updatedPost = commentService.updateComment(id, commentRequestDto);
        logger.info(CommentController.class.getName(), "PUT /api/v1/comment/" + id + " - Comment updated: " + updatedPost);
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
        logger.info(CommentController.class.getName(), "DELETE /api/v1/comment/" + id + " called with path variable id: " + id);
        commentService.deleteCommentById(id);
        logger.info(CommentController.class.getName(), "DELETE /api/v1/comment/" + id + " - Comment deleted.");
        return ResponseEntity.ok().build();
    }
}
