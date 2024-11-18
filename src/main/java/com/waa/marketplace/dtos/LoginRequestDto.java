package com.waa.marketplace.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
