package edu.miu.labs.repositories;

import edu.miu.labs.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN u.posts p GROUP BY u.id HAVING COUNT(p) > 1")
    List<User> getUsersWithMultiplePosts();
}
