package edu.miu.labs.entities.prep;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String address;

    @OneToMany(mappedBy = "member")
    private List<Loan> loans;

    // Getters and setters
}

