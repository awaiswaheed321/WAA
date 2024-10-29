package edu.mid.lab1.entities.dtos;

import lombok.Data;

@Data
public class PostDto {
    private Long id;
    private String content;
    private String title;
    private String author;
}
