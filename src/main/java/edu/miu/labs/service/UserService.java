package edu.miu.labs.service;

import edu.miu.labs.entities.dtos.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto findPostById(long id);
}
