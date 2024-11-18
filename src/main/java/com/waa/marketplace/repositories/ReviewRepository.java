package com.waa.marketplace.repositories;

import com.waa.marketplace.entites.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE " +
            "(:productId IS NULL OR r.product.id = :productId) AND " +
            "(:rating IS NULL OR r.rating = :rating) AND " +
            "(:buyerEmail IS NULL OR r.buyer.user.email = :buyerEmail)")
    List<Review> findReviewsWithFilters(@Param("productId") Long productId,
                                        @Param("rating") Integer rating,
                                        @Param("buyerEmail") String buyerEmail);
}
