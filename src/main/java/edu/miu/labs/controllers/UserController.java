package edu.miu.labs.controllers;

import edu.miu.labs.aspects.annotations.ExecutionTime;
import edu.miu.labs.entities.dtos.*;
import edu.miu.labs.service.LoggerService;
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
    private final LoggerService logger;

    public UserController(UserService userService, LoggerService logger) {
        this.userService = userService;
        this.logger = logger;
    }

    /**
     * Retrieves all users.
     *
     * @return A ResponseEntity containing a list of UserDto if users are found, or no content if empty.
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        logger.info(UserController.class.getName(), "GET /api/v1/user called to retrieve all users.");
        List<UserDto> users = userService.getUsers();
        if (users.isEmpty()) {
            logger.info(UserController.class.getName(), "GET /api/v1/user - No users found.");
            return ResponseEntity.noContent().build();
        } else {
            logger.info(UserController.class.getName(), "GET /api/v1/user - Users retrieved: " + users);
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
        logger.info(UserController.class.getName(), "GET /api/v1/user/" + id + " called with path variable id: " + id);
        UserDto user = userService.findUserById(id);
        if (user == null) {
            logger.info(UserController.class.getName(), "GET /api/v1/user/" + id + " - User not found.");
            return ResponseEntity.noContent().build();
        } else {
            logger.info(UserController.class.getName(), "GET /api/v1/user/" + id + " - User found: " + user);
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
        logger.info(UserController.class.getName(), "POST /api/v1/user called with request body: " + userRequestDto);
        UserDto createdUser = userService.saveUser(userRequestDto);
        logger.info(UserController.class.getName(), "POST /api/v1/user - User created: " + createdUser);
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
        logger.info(UserController.class.getName(), "GET /api/v1/user/" + id + "/posts called with path variable id: " + id);
        List<PostDto> posts = userService.getPostsByUserId(id);
        if (posts.isEmpty()) {
            logger.info(UserController.class.getName(), "GET /api/v1/user/" + id + "/posts - No posts found for user.");
            return ResponseEntity.noContent().build();
        } else {
            logger.info(UserController.class.getName(), "GET /api/v1/user/" + id + "/posts - Posts found: " + posts);
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
        logger.info(UserController.class.getName(), "GET /api/v1/user/multiple-posts called to retrieve users with more than one post.");
        List<UserDto> usersWithMultiplePosts = userService.getUsersWithMultiplePosts();
        if (usersWithMultiplePosts.isEmpty()) {
            logger.info(UserController.class.getName(), "GET /api/v1/user/multiple-posts - No users found with multiple posts.");
            return ResponseEntity.noContent().build();
        } else {
            logger.info(UserController.class.getName(), "GET /api/v1/user/multiple-posts - Users with multiple posts: " + usersWithMultiplePosts);
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
        logger.info(UserController.class.getName(), "DELETE /api/v1/user/" + id + " called with path variable id: " + id);
        userService.deleteUserById(id);
        logger.info(UserController.class.getName(), "DELETE /api/v1/user/" + id + " - User deleted.");
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
        logger.info(UserController.class.getName(), String.format("POST /api/v1/user/%d/post called with request body: %s", id, postRequestDto));
        userService.savePost(id, postRequestDto);
        logger.info(UserController.class.getName(), String.format("POST /api/v1/user/%d/post - Post created successfully.", id));
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
        logger.info(UserController.class.getName(), "GET /api/v1/users/with-posts-more-than/" + n + " called with path variable n: " + n);
        List<UserDto> users = userService.getUserWithMoreThanNPosts(n);
        if (users.isEmpty()) {
            logger.info(UserController.class.getName(), "GET /api/v1/users/with-posts-more-than/" + n + " - No users found with more than " + n + " posts.");
            return ResponseEntity.noContent().build();
        } else {
            logger.info(UserController.class.getName(), "GET /api/v1/users/with-posts-more-than/" + n + " - Users found: " + users.size());
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
        logger.info(UserController.class.getName(), "User search initiated for posts with titles containing: '" + title + "'");
        List<UserDto> users = userService.getUsersWithPostsContainingTitle(title);
        if (users.isEmpty()) {
            logger.info(UserController.class.getName(), "No users found with posts containing title text: '" + title + "'");
            return ResponseEntity.noContent().build();
        } else {
            logger.info(UserController.class.getName(), "Users found with posts matching title text '" + title + "': Count = " + users.size());
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
        logger.info(UserController.class.getName(), "GET /api/v1/user/" + userId + "/posts/" + postId + "/comments/" + commentId + " - Fetching comment with ID: " + commentId + " for user ID: " + userId + " and post ID: " + postId);
        CommentDto comment = userService.getCommentByUserPostCommentId(userId, postId, commentId);
        logger.info(UserController.class.getName(), "GET /api/v1/user/" + userId + "/posts/" + postId + "/comments/" + commentId + " - Comment found: " + comment);
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
        logger.info(UserController.class.getName(), "GET /api/v1/user/" + userId + "/posts/" + postId + " - Fetching post with ID: " + postId + " for user ID: " + userId);
        PostDto post = userService.getPostByUserPostId(userId, postId);
        logger.info(UserController.class.getName(), "GET /api/v1/user/" + userId + "/posts/" + postId + " - Post found: " + post);
        return ResponseEntity.ok(post);
    }


}
