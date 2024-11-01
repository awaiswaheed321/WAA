package edu.miu.labs.service.impl;

import edu.miu.labs.entities.Comment;
import edu.miu.labs.entities.dtos.CommentDto;
import edu.miu.labs.entities.dtos.CommentRequestDto;
import edu.miu.labs.repositories.CommentRepository;
import edu.miu.labs.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto findCommentById(Long id) {
        return commentRepository.findById(id)
                .map(post -> modelMapper.map(post, CommentDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + id));
    }

    @Override
    public void deleteCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Comment not found with id: " + id);
        }
    }

    @Transactional
    @Override
    public CommentDto updateComment(Long id, CommentRequestDto commentRequestDto) {
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setName(commentRequestDto.getName());
                    return modelMapper.map(comment, CommentDto.class);
                })
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + id));
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return modelMapper.map(comments, new TypeToken<List<CommentDto>>() {
        }.getType());
    }
}
