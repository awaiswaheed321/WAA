package edu.miu.labs.entities.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private List<PostDto> posts;
}
