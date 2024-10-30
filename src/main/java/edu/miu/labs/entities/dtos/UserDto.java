package edu.miu.labs.entities.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    Long id;
    String name;
    List<PostDto> posts;
}
