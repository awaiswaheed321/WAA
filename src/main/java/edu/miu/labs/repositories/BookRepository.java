package edu.miu.labs.repositories;

import edu.miu.labs.entities.prep.Book;
import edu.miu.labs.entities.prep.Category;
import edu.miu.labs.entities.prep.LoanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN b.category c WHERE c.name = :name")
    List<Book> getBookByCategoryName(String name);

    @Query("SELECT li FROM Book b JOIN b.loanItems li WHERE b.id = :bookId")
    List<LoanItem> getLoanItemsByBookId(Long bookId);

//    @Query("SELECT b FROM Book b ORDER BY b.price DESC LIMIT 5")
//    List<Book> getTop5ExpensiveBooks();

    List<Book> findAllByPriceLessThan(Double price);

    List<Book> findAllByCategory(Category category);

}
