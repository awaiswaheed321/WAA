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
@Schema(description = "DTO for a seller, including their personal information and registration status.")
public class SellerResponseDto {

    @Schema(description = "Unique identifier for the seller", example = "1")
    private Long id;

    @Schema(description = "First Name of the seller", example = "John Doe")
    private String firstName;

    @Schema(description = "Last Name of the seller", example = "John Doe")
    private String lastName;

    @Schema(description = "Email address of the seller", example = "john.doe@example.com")
    private String email;
}
