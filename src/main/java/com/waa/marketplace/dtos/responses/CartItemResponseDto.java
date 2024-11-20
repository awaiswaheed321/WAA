package com.waa.marketplace.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO for the cart item response.
 */
@Data
public class CartItemResponseDto {

    @Schema(description = "Unique identifier of the cart item", example = "101")
    private Long id;

    @Schema(description = "Name of the product in the cart", example = "Wireless Mouse")
    private String productName;

    @Schema(description = "Quantity of the product in the cart", example = "2")
    private Integer quantity;

    @Schema(description = "Price per unit of the product", example = "20.5")
    private Double pricePerUnit;

    @Schema(description = "Total price for this product (quantity * price)", example = "41.0")
    private Double totalPrice;
}
