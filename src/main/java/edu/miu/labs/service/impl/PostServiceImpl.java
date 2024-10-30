package edu.miu.labs.service.impl;

import edu.miu.labs.entities.Post;
import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.PostRequestDto;
import edu.miu.labs.repositories.PostRepository;
import edu.miu.labs.service.PostService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    final private PostRepository postRepository;
    final private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public List<PostDto> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return modelMapper.map(posts, new TypeToken<List<PostDto>>() {
        }.getType());
    }

    public PostDto savePost(PostRequestDto postRequestDto) {
        Post post = modelMapper.map(postRequestDto, Post.class);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    public PostDto findPostById(Long id) {
        return postRepository.findById(id)
                .map(post -> modelMapper.map(post, PostDto.class))
                .orElse(null);
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostDto updatePost(Long id, PostRequestDto postRequestDto) {
        return postRepository.findById(id).map(existingPost -> {
            existingPost.setContent(postRequestDto.getContent());
            existingPost.setTitle(postRequestDto.getTitle());
            existingPost.setAuthor(postRequestDto.getAuthor());
            Post updatedPost = postRepository.save(existingPost);
            return modelMapper.map(updatedPost, PostDto.class);
        }).orElse(null);
    }

    @Override
    public List<PostDto> getFilteredPostsByAuthorName(String author, String authorContaining) {
        List<Post> posts = new ArrayList<>();
        if (author == null && authorContaining == null) {
            posts = postRepository.findAll();
        } else if (author != null) {
            posts = postRepository.findByAuthorIgnoreCase(author);
        } else {
            posts = postRepository.findByAuthorContainingIgnoreCase(authorContaining);
        }
        return modelMapper.map(posts, new TypeToken<List<PostDto>>() {
        }.getType());
    }
}
