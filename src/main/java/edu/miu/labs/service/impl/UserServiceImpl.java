package edu.miu.labs.service.impl;

import edu.miu.labs.entities.User;
import edu.miu.labs.entities.dtos.UserDto;
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
    public UserDto findPostById(long id) {
        return userRepository.findById(id).map(u -> modelMapper.map(u, UserDto.class)).orElse(null);
    }
}
