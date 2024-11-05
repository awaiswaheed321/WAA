package edu.miu.labs.service.impl;

import edu.miu.labs.entities.Comment;
import edu.miu.labs.entities.Post;
import edu.miu.labs.entities.User;
import edu.miu.labs.entities.dtos.*;
import edu.miu.labs.repositories.UserRepository;
import edu.miu.labs.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return modelMapper.map(users, new TypeToken<List<UserDto>>() {
        }.getType());
    }

    @Override
    public UserDto findUserById(long id) {
        return userRepository.findById(id).map(u -> modelMapper.map(u, UserDto.class)).orElse(null);
    }

    @Override
    public UserDto saveUser(UserRequestDto userRequestDto) {
        User user = modelMapper.map(userRequestDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<PostDto> getPostsByUserId(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
        return (user != null && user.getPosts() != null) ? modelMapper.map(user.getPosts(), new TypeToken<List<PostDto>>() {
        }.getType()) : List.of();
    }

    @Override
    public List<UserDto> getUsersWithMultiplePosts() {
        List<User> users = userRepository.getUsersWithMultiplePosts();
        return modelMapper.map(users, new TypeToken<List<UserDto>>() {
        }.getType());
    }

    @Override
    @Transactional
    public void savePost(long id, PostRequestDto postRequestDto) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Post post = modelMapper.map(postRequestDto, Post.class);
            if (user.getPosts() == null) {
                List<Post> posts = new ArrayList<>();
                posts.add(post);
                user.setPosts(posts);
            } else {
                user.getPosts().add(post);
            }
        } else {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public void deleteUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Post not found with id: " + id);
        }
    }

    @Override
    public List<UserDto> getUserWithMoreThanNPosts(int n) {
        List<User> users = userRepository.getUserWithMoreThanNPosts(n);
        return modelMapper.map(users, new TypeToken<List<UserDto>>() {
        }.getType());
    }

    @Override
    public List<UserDto> getUsersWithPostsContainingTitle(String title) {
        List<User> users = userRepository.getUsersWithPostsContainingTitle(title);
        return modelMapper.map(users, new TypeToken<List<UserDto>>() {
        }.getType());
    }

    @Override
    public CommentDto getCommentByUserPostCommentId(long userId, long postId, long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        Post post = user.getPosts().stream()
                .filter(p -> p.getId() == postId)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + postId + " for User ID: " + userId));
        Comment comment = post.getComments().stream()
                .filter(c -> c.getId() == commentId)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + commentId + " for Post ID: " + postId));
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public PostDto getPostByUserPostId(long userId, long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));
        Post post = user.getPosts().stream()
                .filter(p -> p.getId() == postId)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Post not found with ID: " + postId + " for User ID: " + userId));
        return modelMapper.map(post, PostDto.class);
    }
}
