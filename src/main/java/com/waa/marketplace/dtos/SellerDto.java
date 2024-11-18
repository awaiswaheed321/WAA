package com.waa.marketplace.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO for a seller, including their personal information and registration status.")
public class SellerDto {

    @Schema(description = "Unique identifier for the seller", example = "1")
    private Long id;

    @Schema(description = "Name of the seller", example = "John Doe")
    private String name;

    @Schema(description = "Email address of the seller", example = "john.doe@example.com")
    private String email;
}
