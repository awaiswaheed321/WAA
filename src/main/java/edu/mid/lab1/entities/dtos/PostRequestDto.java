package edu.mid.lab1.entities.dtos;

import lombok.Data;

@Data
public class PostRequestDto {
    private String content;
    private String title;
    private String author;
}
