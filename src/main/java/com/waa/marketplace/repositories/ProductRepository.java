package com.waa.marketplace.repositories;

import com.waa.marketplace.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    List<Product> findBySellerId(Long sellerId);

    Optional<Product> findByIdAndSellerId(Long id, Long sellerId);

    boolean existsByCategoryId(Long id);
}
