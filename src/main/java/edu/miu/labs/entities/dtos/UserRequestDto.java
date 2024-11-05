package edu.miu.labs.entities.dtos;

import edu.miu.labs.entities.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserRequestDto {
    private String name;
    @NotNull
    String email;
    @NotNull
    String password;
    @NotEmpty
    List<Role> roles;
    private List<PostRequestDto> posts;
}
