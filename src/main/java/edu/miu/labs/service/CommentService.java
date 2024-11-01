package edu.miu.labs.service;

import edu.miu.labs.entities.dtos.CommentDto;
import edu.miu.labs.entities.dtos.CommentRequestDto;

import java.util.List;

public interface CommentService {
    CommentDto findCommentById(Long id);

    void deleteCommentById(Long id);

    CommentDto updateComment(Long id, CommentRequestDto commentRequestDto);

    List<CommentDto> getAllComments();
}
