package com.waa.marketplace.repositories;

import com.waa.marketplace.entites.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByProductSellerId(Long sellerId);

    Optional<Order> findByIdAndProductSellerId(Long orderId, Long sellerId);

    Optional<Order> findByIdAndBuyerId(Long orderId, Long buyerId);

    List<Order> findByBuyerId(Long id);

    List<Order> findByProductId(Long id);
}
