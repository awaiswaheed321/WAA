package edu.miu.labs.entities.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserRequestDto {
    private String name;
    private List<PostRequestDto> posts;
}
