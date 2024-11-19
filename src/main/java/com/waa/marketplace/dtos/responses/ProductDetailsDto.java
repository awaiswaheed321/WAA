package com.waa.marketplace.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Data transfer object for Product Details")
public class ProductDetailsDto {
    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Name of the product", example = "Wireless Mouse")
    private String name;

    @NotNull
    @Schema(description = "Description of the product", example = "Ergonomic wireless mouse with long battery life")
    private String description;

    @NotNull
    @Schema(description = "Price of the product", example = "29.99")
    private Double price;

    @NotNull
    @Schema(description = "Stock quantity of the product", example = "100")
    private int stock;

    @NotNull
    @Schema(description = "Category ID of the product", example = "1")
    private long categoryId;
    @Schema(description = "Seller Info")
    SellerResponseDto seller;
    @Schema(description = "List of reviews")
    List<ReviewResponseDto> reviews;
}
