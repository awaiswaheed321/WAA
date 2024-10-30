package edu.miu.labs.service.impl;

import edu.miu.labs.entities.User;
import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.UserDto;
import edu.miu.labs.entities.dtos.UserRequestDto;
import edu.miu.labs.repositories.UserRepository;
import edu.miu.labs.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
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
}
