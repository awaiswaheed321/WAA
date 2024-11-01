package edu.miu.labs.entities.dtos;

import edu.miu.labs.entities.Comment;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private Long id;
    private String content;
    private String title;
    private String author;
    private List<CommentDto> comments;
}
