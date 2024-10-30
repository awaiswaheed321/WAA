package edu.miu.labs.service;

import edu.miu.labs.entities.Post;
import edu.miu.labs.entities.User;
import edu.miu.labs.repositories.PostRepository;
import edu.miu.labs.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DataGenerationService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public DataGenerationService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
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

        User user1 = new User();
        user1.setName("User One");
        user1.setPosts(Arrays.asList(post1, post2));

        User user2 = new User();
        user2.setName("User Two");
        user2.setPosts(List.of(post3));

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        userRepository.save(user1);
        userRepository.save(user2);
    }
}
