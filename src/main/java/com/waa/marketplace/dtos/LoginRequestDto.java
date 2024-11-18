package com.waa.marketplace.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO for login request containing user credentials.")
public class LoginRequestDto {

    @NotNull
    @Schema(description = "User's email address used for authentication.", example = "user@example.com")
    private String email;

    @NotNull
    @Schema(description = "User's password used for authentication.", example = "P@ssw0rd")
    private String password;
}
