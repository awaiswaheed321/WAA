package edu.mid.lab1.service.impl;

import edu.mid.lab1.entities.Post;
import edu.mid.lab1.entities.dtos.PostDto;
import edu.mid.lab1.entities.dtos.PostRequestDto;
import edu.mid.lab1.repositories.PostRepository;
import edu.mid.lab1.service.PostService;
import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void addSampleData() {
        // Sample Post objects
        Post post1 = new Post();
        post1.setContent("This is the content of the first post.");
        post1.setTitle("First Post Title");
        post1.setAuthor("Alice");

        Post post2 = new Post();
        post2.setContent("This is the content of the second post.");
        post2.setTitle("Second Post Title");
        post2.setAuthor("Bob");

        Post post3 = new Post();
        post3.setContent("This is the content of the third post.");
        post3.setTitle("Third Post Title");
        post3.setAuthor("Charlie");

        Post post4 = new Post();
        post4.setContent("This is the content of the fourth post.");
        post4.setTitle("Fourth Post Title");
        post4.setAuthor("David");

        Post post5 = new Post();
        post5.setContent("This is the content of the fifth post.");
        post5.setTitle("Fifth Post Title");
        post5.setAuthor("Eve");

        // Save the posts to the database
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
    }
}
