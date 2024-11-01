package edu.miu.labs.service.impl;

import edu.miu.labs.entities.Comment;
import edu.miu.labs.entities.Post;
import edu.miu.labs.entities.dtos.CommentRequestDto;
import edu.miu.labs.entities.dtos.PostDto;
import edu.miu.labs.entities.dtos.PostRequestDto;
import edu.miu.labs.repositories.PostRepository;
import edu.miu.labs.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    final private PostRepository postRepository;
    final private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public PostDto findPostById(Long id) {
        return postRepository.findById(id)
                .map(post -> modelMapper.map(post, PostDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
    }

    public void deletePostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            postRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Post not found with id: " + id);
        }
    }

    @Transactional
    @Override
    public PostDto updatePost(Long id, PostRequestDto postRequestDto) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setContent(postRequestDto.getContent());
                    post.setTitle(postRequestDto.getTitle());
                    post.setAuthor(postRequestDto.getAuthor());
                    return modelMapper.map(post, PostDto.class);
                })
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + id));
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

    @Override
    @Transactional
    public void saveComment(long id, CommentRequestDto commentRequestDto) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            Comment comment = modelMapper.map(commentRequestDto, Comment.class);
            if (post.getComments() == null) {
                List<Comment> posts = new ArrayList<>();
                posts.add(comment);
                post.setComments(posts);
            } else {
                post.getComments().add(comment);
            }
        } else {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public List<PostDto> getPostsMatchingTitle(String partialTitle) {
        List<Post> posts = postRepository.getPostsMatchingTitle(partialTitle);
        return modelMapper.map(posts, new TypeToken<List<PostDto>>() {
        }.getType());
    }
}
