package com.waa.marketplace.repositories;

import com.waa.marketplace.entites.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findByApprovedFalse();

    Optional<Seller> findByUserId(Long id);
}
