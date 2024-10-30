package edu.miu.labs.repositories;

import edu.miu.labs.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorIgnoreCase(String author);

    List<Post> findByAuthorContainingIgnoreCase(String partialAuthor);
}
