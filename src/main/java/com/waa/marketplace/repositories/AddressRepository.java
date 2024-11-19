package com.waa.marketplace.repositories;

import com.waa.marketplace.entites.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query("SELECT a FROM Address a WHERE a.buyer.id = :buyerId AND a.type = :type")
    List<Address> findByBuyerIdAndType(@Param("buyerId") Long buyerId, @Param("type") String type);

    List<Address> findByBuyerId(Long id);

    Optional<Address> findByIdAndBuyerId(Long id, Long buyerId);
}
