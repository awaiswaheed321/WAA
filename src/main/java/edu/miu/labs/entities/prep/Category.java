package edu.miu.labs.entities.prep;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Book> books;

    // Default constructor
    public Category() {}

    // Constructor to initialize name
    public Category(String name) {
        this.name = name;
    }

    // Getters and setters
}

