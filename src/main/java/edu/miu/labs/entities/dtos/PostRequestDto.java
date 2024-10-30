package edu.miu.labs.entities.dtos;

import lombok.Data;

@Data
public class PostRequestDto {
    private String content;
    private String title;
    private String author;
}
