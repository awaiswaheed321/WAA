package edu.miu.labs.service;

import edu.miu.labs.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TestDataRunner implements CommandLineRunner {
    private final MemberRepository memberRepository;
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LoanItemRepository loanItemRepository;
    private final CategoryRepository categoryRepository;
    private final PracticeDataGeneration practiceDataGeneration;

    public TestDataRunner(CategoryRepository categoryRepository, LoanItemRepository loanItemRepository, BookRepository bookRepository, LoanRepository loanRepository, MemberRepository memberRepository, PracticeDataGeneration practiceDataGeneration) {
        this.categoryRepository = categoryRepository;
        this.loanItemRepository = loanItemRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.memberRepository = memberRepository;
        this.practiceDataGeneration = practiceDataGeneration;
    }

    @Override
    public void run(String... args) throws Exception {
        practiceDataGeneration.generateData();
    }
}
