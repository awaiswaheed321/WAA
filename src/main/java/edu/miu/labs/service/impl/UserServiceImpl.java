package edu.miu.labs.service.impl;

import edu.miu.labs.entities.Post;
import edu.miu.labs.entities.User;
import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.PostRequestDto;
import edu.miu.labs.entities.dtos.UserDto;
import edu.miu.labs.entities.dtos.UserRequestDto;
import edu.miu.labs.repositories.PostRepository;
import edu.miu.labs.repositories.UserRepository;
import edu.miu.labs.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
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
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<PostDto> getPostsByUserId(long id) {
        User user = userRepository.findById(id).orElse(null);
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
}
