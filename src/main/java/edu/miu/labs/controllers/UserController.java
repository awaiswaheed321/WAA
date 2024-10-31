package edu.miu.labs.controllers;

import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.UserDto;
import edu.miu.labs.entities.dtos.UserRequestDto;
import edu.miu.labs.service.LoggerService;
import edu.miu.labs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    private final LoggerService logger;

    public UserController(UserService userService, LoggerService logger) {
        this.userService = userService;
        this.logger = logger;
    }

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

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        logger.info(UserController.class.getName(), "POST /api/v1/user called with request body: " + userRequestDto);
        UserDto createdUser = userService.saveUser(userRequestDto);
        logger.info(UserController.class.getName(), "POST /api/v1/user - User created: " + createdUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

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
}
