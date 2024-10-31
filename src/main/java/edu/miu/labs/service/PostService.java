package edu.miu.labs.service;

import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.PostRequestDto;

import java.util.List;

public interface PostService {

    PostDto findPostById(Long id);

    void deletePostById(Long id);

    PostDto updatePost(Long id, PostRequestDto postRequestDto);

    List<PostDto> getFilteredPostsByAuthorName(String authorName, String authorContaining);
}
