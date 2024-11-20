package com.waa.marketplace.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "DTO for creating an order")
public class OrderRequestDto {

    @NotNull
    @Schema(description = "ID of the product to order", example = "101")
    private Long productId;

    @NotNull
    @Schema(description = "Quantity of the product to order", example = "2")
    private Integer quantity;

    @NotNull
    @Schema(description = "ID of the billing address", example = "1")
    private Long billingAddressId;

    @NotNull
    @Schema(description = "ID of the shipping address", example = "2")
    private Long shippingAddressId;
}
