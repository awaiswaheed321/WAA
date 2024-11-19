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
@Schema(description = "Data transfer object for buyer signup response")
public class BuyerResponseDto {
    @Schema(description = "Id of the buyer", example = "1")
    Long id;

    @Schema(description = "Name of the buyer", example = "John Doe")
    private String name;

    @Schema(description = "Email address of the buyer", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Shipping address of the buyer", example = "123 Main St, Springfield, IL 62701")
    private String shippingAddress;

    @Schema(description = "Billing address of the buyer", example = "123 Main St, Springfield, IL 62701")
    private String billingAddress;
}
