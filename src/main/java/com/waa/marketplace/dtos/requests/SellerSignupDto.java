package com.waa.marketplace.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data transfer object for seller signup")
public class SellerSignupDto {
    @NotNull
    @Schema(description = "Name of the seller", example = "John Doe")
    private String name;

    @NotNull
    @Schema(description = "Email address of the seller", example = "john.doe@example.com")
    private String email;

    @NotNull
    @Schema(description = "Password for the seller's account", example = "securePassword123")
    private String password;
}
