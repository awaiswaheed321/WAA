package com.waa.marketplace.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data transfer object for Order")
public class OrderResponseDto {

    @Schema(description = "Unique identifier of the order", example = "1")
    private Long id;

    @Schema(description = "Product associated with the order", example = "Wireless Mouse")
    private ProductResponseDto product;

    @Schema(description = "Quantity of the product ordered", example = "2")
    private int quantity;

    @Schema(description = "Current status of the order", example = "Pending")
    private String status;

    @Schema(description = "Total amount for the order", example = "59.98")
    private Double totalAmount;
}
