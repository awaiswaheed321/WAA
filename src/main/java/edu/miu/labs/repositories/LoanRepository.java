package edu.miu.labs.repositories;

import edu.miu.labs.entities.prep.Loan;
import edu.miu.labs.entities.prep.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT l FROM Loan l WHERE l.loanDate >= :startDate AND l.loanDate <= :endDate")
    List<Loan> getLoansBetweenDates(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(b) FROM Loan l JOIN l.loanItems li JOIN li.book b GROUP BY b")
    List<Double> getTotalQuantityOfBookAcrossAllLoans();

    List<Loan> findLoanByMemberId(Long id);

    List<Loan> findAllByLoanDate(LocalDate loanDate);
}
