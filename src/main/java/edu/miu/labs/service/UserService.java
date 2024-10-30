package edu.miu.labs.service;

import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.UserDto;
import edu.miu.labs.entities.dtos.UserRequestDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto findUserById(long id);

    UserDto saveUser(UserRequestDto userRequestDto);

    List<PostDto> getPostsByUserId(long id);
}
