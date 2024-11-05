package edu.miu.labs.entities.dtos;

import edu.miu.labs.entities.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private List<Role> roles;
    private List<PostDto> posts;
}
