package edu.miu.labs.controllers;

import edu.miu.labs.aspects.annotations.ExecutionTime;
import edu.miu.labs.entities.dtos.*;
import edu.miu.labs.service.UserService;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing users and their posts.
 * Provides endpoints to retrieve, create, and update user data and associated posts.
 */
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Retrieves all users.
     *
     * @return A ResponseEntity containing a list of UserDto if users are found, or no content if empty.
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = userService.getUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    /**
     * Retrieves a specific user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A ResponseEntity containing the UserDto if found, or no content if not found.
     */
    @ExecutionTime
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable long id) {
        UserDto user = userService.findUserById(id);
        if (user == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }

    /**
     * Creates a new user.
     *
     * @param userRequestDto The data for the user to create.
     * @return A ResponseEntity with the created UserDto and HTTP status 201.
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserDto createdUser = userService.saveUser(userRequestDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /**
     * Retrieves posts associated with a specific user by ID.
     *
     * @param id The ID of the user whose posts are to be retrieved.
     * @return A ResponseEntity containing a list of PostDto if posts are found, or no content if empty.
     */
    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDto>> getPosts(@PathVariable long id) {
        List<PostDto> posts = userService.getPostsByUserId(id);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(posts);
        }
    }

    /**
     * Retrieves users who have more than one post.
     *
     * @return A ResponseEntity containing a list of UserDto with multiple posts, or no content if none found.
     */
    @GetMapping("/multiple-posts")
    public ResponseEntity<List<UserDto>> getUsersWithMoreThanOnePosts() {
        List<UserDto> usersWithMultiplePosts = userService.getUsersWithMultiplePosts();
        if (usersWithMultiplePosts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usersWithMultiplePosts);
        }
    }

    /**
     * Deletes a specific user by its ID.
     *
     * @param id The ID of the user to delete.
     * @return A ResponseEntity with a success status if deletion is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Creates a new post for a specific user by ID.
     *
     * @param id             The ID of the user for whom to create the post.
     * @param postRequestDto The data for the post to create.
     * @return A ResponseEntity with HTTP status 201 if the post is successfully created.
     */
    @PostMapping("/{id}/post")
    public ResponseEntity<PostDto> createPost(@PathVariable long id, @RequestBody PostRequestDto postRequestDto) {
        userService.savePost(id, postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Retrieves users who have more than the specified number of posts.
     *
     * @param n The minimum number of posts a user must have (must be 0 or greater).
     * @return A ResponseEntity containing a list of UserDto if users are found,
     * or a no content status if no users meet the criteria.
     */
    @GetMapping("/with-posts-more-than/{n}")
    public ResponseEntity<List<UserDto>> getUserWithMoreThanNPosts(@PathVariable @Min(0) int n) {
        List<UserDto> users = userService.getUserWithMoreThanNPosts(n);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    /**
     * Retrieves users who have posts with titles containing the specified text (case-insensitive).
     *
     * @param title The text to search for within the titles of users' posts.
     * @return A ResponseEntity containing a list of UserDto if users with matching posts are found,
     * or a no content status if no matches are found.
     */
    @GetMapping("/posts/title-contains/{title}")
    public ResponseEntity<List<UserDto>> getUsersWithPostsContainingTitle(@PathVariable String title) {
        List<UserDto> users = userService.getUsersWithPostsContainingTitle(title);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(users);
        }
    }

    /**
     * Retrieves a specific comment associated with a given user and post by their IDs.
     *
     * @param userId    The ID of the user who owns the post.
     * @param postId    The ID of the post to which the comment belongs.
     * @param commentId The ID of the comment to retrieve.
     * @return A ResponseEntity containing the CommentDto if found.
     */
    @GetMapping("/{userId}/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentByUserPostCommentId(@PathVariable long userId, @PathVariable long postId, @PathVariable long commentId) {
        CommentDto comment = userService.getCommentByUserPostCommentId(userId, postId, commentId);
        return ResponseEntity.ok(comment);
    }

    /**
     * Retrieves a specific post associated with a given user by their IDs.
     *
     * @param userId The ID of the user who owns the post.
     * @param postId The ID of the post to be retrieved.
     * @return A ResponseEntity containing the PostDto if found, or not found status if the post does not exist.
     */
    @GetMapping("/{userId}/posts/{postId}")
    public ResponseEntity<PostDto> getPostByUserPostId(@PathVariable long userId, @PathVariable long postId) {
        PostDto post = userService.getPostByUserPostId(userId, postId);
        return ResponseEntity.ok(post);
    }

    /**
     * GET endpoint for testing purposes to verify that the ExceptionAspect
     * correctly logs exceptions to the database. This method intentionally
     * throws a RuntimeException with a test message to confirm logging behavior.
     */
    @GetMapping("exception")
    public void getTestException() {
        throw new RuntimeException("This is exception message for testing purposes");
    }
}
