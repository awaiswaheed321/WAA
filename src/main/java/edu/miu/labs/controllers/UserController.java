package edu.miu.labs.controllers;

import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.PostRequestDto;
import edu.miu.labs.entities.dtos.UserDto;
import edu.miu.labs.entities.dtos.UserRequestDto;
import edu.miu.labs.service.LoggerService;
import edu.miu.labs.service.UserService;
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
}
