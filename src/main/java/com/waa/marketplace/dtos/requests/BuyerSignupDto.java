package com.waa.marketplace.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data transfer object for buyer signup")
public class BuyerSignupDto {

    @NotNull
    @Schema(description = "Name of the buyer", example = "John Doe")
    private String name;

    @NotNull
    @Schema(description = "Email address of the buyer", example = "john.doe@example.com")
    private String email;

    @NotNull
    @Schema(description = "Password for the buyer's account", example = "securePassword123")
    private String password;

    @NotNull
    @Schema(description = "Shipping address of the buyer", example = "123 Main St, Springfield, IL 62701")
    private String shippingAddress;

    @NotNull
    @Schema(description = "Billing address of the buyer", example = "123 Main St, Springfield, IL 62701")
    private String billingAddress;
}

