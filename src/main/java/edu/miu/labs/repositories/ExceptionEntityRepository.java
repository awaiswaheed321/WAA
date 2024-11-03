package edu.miu.labs.repositories;

import edu.miu.labs.entities.ExceptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionEntityRepository extends JpaRepository<ExceptionEntity, Long> {
}
