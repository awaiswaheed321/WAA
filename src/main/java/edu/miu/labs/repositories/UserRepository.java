package edu.miu.labs.repositories;

import edu.miu.labs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE SIZE(u.posts) > 1")
    List<User> getUsersWithMultiplePosts();

    @Query("SELECT u FROM User u WHERE SIZE(u.posts) > :n")
    List<User> getUserWithMoreThanNPosts(int n);

    @Query("SELECT u FROM User u JOIN u.posts p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<User> getUsersWithPostsContainingTitle(String title);

    User findByEmail(String username);
}
