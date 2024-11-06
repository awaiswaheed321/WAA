package edu.miu.labs.repositories;

import edu.miu.labs.entities.prep.LoanItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanItemRepository extends JpaRepository<LoanItem, Long> {

    List<LoanItem> findByBook_Title(String name);
}
