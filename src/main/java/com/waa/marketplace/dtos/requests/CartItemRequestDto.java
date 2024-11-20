package com.waa.marketplace.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO for adding or updating an item in the cart.
 */
@Data
public class CartItemRequestDto {

    @NotNull(message = "Product ID cannot be null.")
    @Schema(description = "ID of the product to be added to the cart", example = "1")
    private Long productId;

    @Min(value = 1, message = "Quantity must be at least 1.")
    @Schema(description = "Quantity of the product to be added", example = "2")
    private Integer quantity;
}
