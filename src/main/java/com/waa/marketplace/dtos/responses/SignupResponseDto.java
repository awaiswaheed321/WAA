package com.waa.marketplace.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "DTO for successful signup user.")
public class SignupResponseDto {

    @Schema(description = "Unique identifier for the user", example = "1")
    private Long id;

    @Schema(description = "First Name of the user", example = "John")
    private String firstName;

    @Schema(description = "Last Name of the user", example = "John")
    private String lastName;

    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;
}