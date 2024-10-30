package edu.miu.labs.controllers;

import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.UserDto;
import edu.miu.labs.entities.dtos.UserRequestDto;
import edu.miu.labs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> dtos = userService.getUsers();
        if (dtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(dtos);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getPost(@PathVariable long id) {
        UserDto p = userService.findUserById(id);
        if (p == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(p);
        }
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserDto createdUser = userService.saveUser(userRequestDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<List<PostDto>> getPosts(@PathVariable long id) {
        List<PostDto> posts = userService.getPostsByUserId(id);
        if (posts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(posts);
        }
    }
}
