package edu.miu.labs.repositories;

import edu.miu.labs.entities.prep.Book;
import edu.miu.labs.entities.prep.Loan;
import edu.miu.labs.entities.prep.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT DISTINCT m FROM Member m JOIN m.loans l JOIN l.loanItems li JOIN li.book b")
    List<Member> getMembersWithAtLeastOneBorrowedBook();

    @Query("SELECT SUM(li.fineAmount) FROM Member m JOIN m.loans l JOIN l.loanItems li where m.email = :email")
    Long getFineAmountByEmail(String email);

    @Query("SELECT AVG(li.fineAmount) FROM Member m JOIN m.loans l JOIN l.loanItems li GROUP BY m")
    List<Double> getAverageFineAmountForEachMember();

    @Query("SELECT b FROM Member m JOIN m.loans l JOIN l.loanItems li JOIN li.book b WHERE m.email = :email")
    List<Book> getBorrowedBooksByMember(String email);

    @Query("SELECT m from Member m JOIN m.loans l JOIN l.loanItems li JOIN li.book b JOIN b.category c where c.name = :category")
    List<Member> getMembersBorrowingBooksByCategory(String category);

    Member findByEmail(String email);

    List<Member> findAllByEmailContaining(String email);


}
