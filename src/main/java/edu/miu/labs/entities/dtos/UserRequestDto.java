package edu.miu.labs.entities.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDto {
    private String name;
    @NotNull
    String email;
    @NotNull
    String password;
}
