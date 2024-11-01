package edu.miu.labs.service;

import edu.miu.labs.entities.Comment;
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
        Comment comment1 = new Comment();
        comment1.setName("This is comment 1");

        Comment comment2 = new Comment();
        comment2.setName("This is comment 2");

        Comment comment3 = new Comment();
        comment3.setName("This is comment 3");

        Comment comment4 = new Comment();
        comment4.setName("This is comment 4");

        Comment comment5 = new Comment();
        comment5.setName("This is comment 5");

        Comment comment6 = new Comment();
        comment6.setName("This is comment 6");

        Comment comment7 = new Comment();
        comment7.setName("This is comment 7");


        Post post1 = new Post();
        post1.setContent("This is the content of the first post.");
        post1.setTitle("First Post Title");
        post1.setAuthor("Alice");
        post1.setComments(Arrays.asList(comment1, comment2, comment3));

        Post post2 = new Post();
        post2.setContent("This is the content of the second post.");
        post2.setTitle("Second Post Title");
        post2.setAuthor("Bob");
        post2.setComments(Arrays.asList(comment4, comment5));

        Post post3 = new Post();
        post3.setContent("This is the content of the third post.");
        post3.setTitle("Third Post Title");
        post3.setAuthor("Charlie");
        post3.setComments(Arrays.asList(comment6, comment7));

        User user1 = new User();
        user1.setName("User One");
        user1.setPosts(Arrays.asList(post1, post2));

        User user2 = new User();
        user2.setName("User Two");
        user2.setPosts(List.of(post3));

        userRepository.save(user1);
        userRepository.save(user2);
    }
}
