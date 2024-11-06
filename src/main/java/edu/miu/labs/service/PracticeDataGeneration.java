package edu.miu.labs.service;

import edu.miu.labs.entities.prep.*;
import edu.miu.labs.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PracticeDataGeneration {
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LoanItemRepository loanItemRepository;
    private final CategoryRepository categoryRepository;

    private final Random random = new Random();

    public PracticeDataGeneration(CategoryRepository categoryRepository, LoanItemRepository loanItemRepository, BookRepository bookRepository, LoanRepository loanRepository, MemberRepository memberRepository) {
        this.categoryRepository = categoryRepository;
        this.loanItemRepository = loanItemRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void generateData() {
        generateCategories();
        generateBooks();
        generateMembers();
        generateLoans();
    }

    private void generateCategories() {
        List<Category> categories = List.of(
                new Category("Science Fiction"),
                new Category("Fantasy"),
                new Category("History"),
                new Category("Romance"),
                new Category("Thriller")
        );
        categoryRepository.saveAll(categories);
    }

    private void generateBooks() {
        List<Category> categories = categoryRepository.findAll();
        List<Book> books = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            Book book = new Book();
            book.setTitle("Book Title " + i);
            book.setAuthor("Author " + i);
            book.setPrice(10 + random.nextDouble() * 90); // Random price between 10 and 100
            book.setCategory(categories.get(random.nextInt(categories.size()))); // Assign random category
            books.add(book);
        }
        bookRepository.saveAll(books);
    }

    private void generateMembers() {
        List<Member> members = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            Member member = new Member();
            member.setName("Member " + i);
            member.setEmail("member" + i + "@library.com");
            member.setAddress("Address " + i);
            members.add(member);
        }
        memberRepository.saveAll(members);
    }

    private void generateLoans() {
        List<Member> members = memberRepository.findAll();
        List<Book> books = bookRepository.findAll();
        List<Loan> loans = new ArrayList<>();

        for (Member member : members) {
            // Each member gets 1 to 3 loans
            int loanCount = 1 + random.nextInt(3);

            for (int j = 0; j < loanCount; j++) {
                Loan loan = new Loan();
                loan.setLoanDate(LocalDate.now().minusDays(random.nextInt(30))); // Date within the past month
                loan.setReturnDate(loan.getLoanDate().plusDays(14)); // Fixed return date 14 days later
                loan.setTotalFine(random.nextDouble() * 20); // Random fine between 0 and 20
                loan.setMember(member);
                loans.add(loan);

                // Generate LoanItems for each Loan
                generateLoanItems(loan, books);
            }
        }
        loanRepository.saveAll(loans);
    }

    private void generateLoanItems(Loan loan, List<Book> books) {
        int itemCount = 1 + random.nextInt(5); // Each loan has 1 to 5 items
        List<LoanItem> loanItems = new ArrayList<>();

        for (int i = 0; i < itemCount; i++) {
            LoanItem loanItem = new LoanItem();
            loanItem.setLoan(loan);
            loanItem.setBook(books.get(random.nextInt(books.size()))); // Random book
            loanItem.setQuantity(1 + random.nextInt(3)); // Quantity between 1 and 3
            loanItem.setFineAmount(random.nextDouble() * 5); // Random fine amount between 0 and 5
            loanItems.add(loanItem);
        }
        loanItemRepository.saveAll(loanItems);
    }
}
