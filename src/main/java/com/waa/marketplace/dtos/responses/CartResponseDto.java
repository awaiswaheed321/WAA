package com.waa.marketplace.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * DTO for the entire cart response, including all items in the cart.
 */
@Data
public class CartResponseDto {

    @Schema(description = "List of items in the cart")
    private List<CartItemResponseDto> items;

    @Schema(description = "Total price of all items in the cart", example = "200.0")
    private Double totalPrice;
}
