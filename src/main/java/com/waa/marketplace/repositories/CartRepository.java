package com.waa.marketplace.repositories;

import com.waa.marketplace.entites.Buyer;
import com.waa.marketplace.entites.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}
