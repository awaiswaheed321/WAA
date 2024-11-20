package com.waa.marketplace.repositories;

import com.waa.marketplace.entites.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByBuyerId(Long id);

    Optional<Address> findByIdAndBuyerId(Long id, Long buyerId);
}
