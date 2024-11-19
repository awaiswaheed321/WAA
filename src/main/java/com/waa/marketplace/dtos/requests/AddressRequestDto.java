package com.waa.marketplace.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO for Add Address Request")
public class AddressRequestDto {
    @NotNull
    @Schema(description = "Street address", example = "123 Main St")
    private String street;

    @NotNull
    @Schema(description = "City name", example = "Springfield")
    private String city;

    @NotNull
    @Schema(description = "State name", example = "Illinois")
    private String state;

    @NotNull
    @Schema(description = "ZIP code", example = "62704")
    private String zipCode;

    @NotNull
    @Schema(description = "Country name", example = "USA")
    private String country;

    @NotNull
    @Schema(description = "Type of address, e.g., 'billing' or 'shipping'", example = "billing")
    private String type;
}

