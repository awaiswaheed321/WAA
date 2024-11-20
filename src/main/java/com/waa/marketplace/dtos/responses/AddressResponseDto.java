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
public class AddressResponseDto {
    @Schema(description = "Unique identifier for the address", example = "1")
    private Long id;

    @Schema(description = "Street address", example = "123 Main St")
    private String street;

    @Schema(description = "City name", example = "Springfield")
    private String city;

    @Schema(description = "State name", example = "Illinois")
    private String state;

    @Schema(description = "ZIP code", example = "62704")
    private String zipCode;

    @Schema(description = "Country name", example = "USA")
    private String country;
}
