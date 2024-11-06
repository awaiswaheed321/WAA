package edu.miu.labs.entities.prep;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class LoanItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;
    private Double fineAmount;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    // Getters and setters
}

