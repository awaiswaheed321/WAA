package com.waa.marketplace.repositories;

import com.waa.marketplace.entites.ExceptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionEntityRepository extends JpaRepository<ExceptionEntity, Long> {
}
