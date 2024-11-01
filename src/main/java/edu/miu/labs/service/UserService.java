package edu.miu.labs.service;

import edu.miu.labs.entities.dtos.*;

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

    CommentDto getCommentByUserPostCommentId(long userId, long postId, long commentId);

    PostDto getPostByUserPostId(long userId, long postId);
}
