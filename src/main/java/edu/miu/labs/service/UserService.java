package edu.miu.labs.service;

import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.PostRequestDto;
import edu.miu.labs.entities.dtos.UserDto;
import edu.miu.labs.entities.dtos.UserRequestDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    UserDto findUserById(long id);

    UserDto saveUser(UserRequestDto userRequestDto);

    List<PostDto> getPostsByUserId(long id);

    List<UserDto> getUsersWithMultiplePosts();

    void savePost(long id, PostRequestDto postRequestDto);

    void deleteUserById(long id);

    List<UserDto> getUserWithMoreThanNPosts(int n);

    List<UserDto> getUsersWithPostsContainingTitle(String title);
}
